(ns tamagotchi-clojure.core
  (:import java.util.Date))

(def init-health 10) ;; Health of a new tamagotchi
(def feed-time 20000) ;; Time (millis) before health is reduced, if not fed
(def health-points-feed 2) ;; How much + health on feed
(def health-points-subtract 2) ;; How much - health on forgot to feed


(declare next-step)


(defn main-menu-prompt []
  (println "create <name> ::: creates a new tamagotchi")
  (println "show ::: shows tamagotchi")
  (println "feed ::: feeds tamagotchi")
  (println "quit ::: exit"))

(defn create [name]
  {:name name :health init-health :lft (System/currentTimeMillis)})

(defn create-with-msg [name]
  (let [tamagotchi (create name)]
    (println (str "Tamagotchi " (:name tamagotchi) " created!"))
    tamagotchi))

(defn format-date [millis]
  (.format (java.text.SimpleDateFormat. "dd.MM.yyyy hh:mm:ss") (Date. millis)))


(defn get-status-str [tamagotchi]
  (str (:name tamagotchi) " has " (:health tamagotchi) " health points. Last feed time: " (format-date (:lft tamagotchi)) ", You have "
       (quot (- feed-time (- (System/currentTimeMillis) (:lft tamagotchi))) 1000) " seconds to feed it before health is reduced."))

(defn show-status [tamagotchi]
  (println (get-status-str tamagotchi))
  tamagotchi)

(defn add-health [tamagotchi health]
  (update-in tamagotchi [:health] + health))

(defn reset-feed-time [tamagotchi]
  (assoc tamagotchi :lft (System/currentTimeMillis)))

(defn feed [tamagotchi]
  (-> tamagotchi
      (add-health health-points-feed)
      (reset-feed-time)))

(defn subtract-health [tamagotchi health]
  (update-in tamagotchi [:health] - health))


(defn feed-and-print [tamagotchi]
  (let [tamagotchi (feed tamagotchi)]
    (println (str "Fed!, updated health: " (:health tamagotchi)))
    tamagotchi))

(defn update-tamagotchi [tamagotchi]
  (let [current-time (System/currentTimeMillis)
        time-since-last-feed (- current-time (:lft tamagotchi))
        health-to-subtract
        (if (> time-since-last-feed feed-time)
          (* health-points-subtract (quot time-since-last-feed feed-time))
          0)]

    (if (> health-to-subtract 0)
      (do
        (let [updated-tamagotchi (-> tamagotchi
                            (subtract-health health-to-subtract)
                            (reset-feed-time))
              msg (str "You didn't feed " (:name tamagotchi) " in the last " (* (quot time-since-last-feed feed-time) (quot feed-time 1000)) " seconds! " (:name tamagotchi) " suffered a health loss of " health-to-subtract " points.")]


          (if (<= (:health updated-tamagotchi) 0)
            (do
              (println (str msg ". " (:name tamagotchi) " had no health left and died."))
              nil) ;; Death
            (do
              (println (str msg "\nNew health: " (:health updated-tamagotchi)))
              updated-tamagotchi) ;; Reduced health
            ) 
          ))
      
      tamagotchi ;; No particular events - return tamagotchi unchanged 

)))

(defn handle-requires-tamagotchi-command [tamagotchi function]
  (if (nil? tamagotchi)
    (do
      (println "You don't have a tamagotchi. Please create one with 'create <name>'")
      (next-step nil)) ;; Continue input loop
    (function)))

(defn next-step [tamagotchi]

  (let [
        line (read-line)
        tamagotchi (when-not (nil? tamagotchi) (update-tamagotchi tamagotchi))
        tokens (clojure.string/split line #"\s+")
        command-str (nth tokens 0)
        command (keyword command-str)]

    (case command
      :create
      (if (= (count tokens) 2)
        (next-step (create-with-msg (nth tokens 1)))
        (do
          (println "Wrong arguments. Format: create <name>")))

      :show
      (handle-requires-tamagotchi-command
       tamagotchi
       #(-> tamagotchi
            show-status
            next-step))

      :feed
      (handle-requires-tamagotchi-command
       tamagotchi
       #(-> tamagotchi
            feed-and-print
            next-step))
      
      :quit
      (println "Bye bye!")

      (do
        (println "Valid commands: create <name> | show | feed | quit")
        (next-step tamagotchi)))))


(defn -main [& args]
  (main-menu-prompt)
  (next-step nil))
