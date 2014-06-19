tamagotchi_clojure
==================


A basic example of a command-line Tamagotchi game, in Clojure!

Completely functional - doesn't mantain any state (besides the "state" in the functions itself, of course).


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
Max has 10 health points. Last feed time: 20.06.2014 12:21:09, You have 15 seconds to feed it before health is reduced.
show
Max has 10 health points. Last feed time: 20.06.2014 12:21:09, You have 11 seconds to feed it before health is reduced.
feed
Feed!, updated health: 12
show
Max has 12 health points. Last feed time: 20.06.2014 12:21:22, You have 17 seconds to feed it before health is reduced.
show
You didn't feed Max in the last 20 seconds! Max suffered a health loss of 2 points.
New health: 10
Max has 10 health points. Last feed time: 20.06.2014 12:22:01, You have 19 seconds to feed it before health is reduced.
```

