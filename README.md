tamagotchi_clojure
==================


A basic command-line example of a Tamagotchi (-like) game, in Clojure!

Completely functional - doesn't mantain any state!


Start:

```
Lein run
```


This is how it looks:

```
create <name> ::: creates a new tamagotchi
show ::: shows tamagotchi
feed ::: feeds tamagotchi
quit ::: exit
create Max
Tamagotchi Max created!
show
Max has 10 health points. Last feed time: 19.06.2014 11:51:28, You have 17 seconds to feed it before health is reduced.
show
Max has 10 health points. Last feed time: 19.06.2014 11:51:28, You have 9 seconds to feed it before health is reduced.
feed
Feed!, updated health: 12
show
Max has 12 health points. Last feed time: 19.06.2014 11:51:42, You have 15 seconds to feed it before health is reduced.
```

