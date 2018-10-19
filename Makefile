javaproj: Barrier.class Bomb.class Bullet.class Enemy.class Gun.class LCU.class Main.class

Barrier.class: Barrier.java
	javac -g Barrier.java

Bomb.class: Bomb.java
	javac -g Bomb.java

Bullet.class: Bullet.java
	javac -g Bullet.java

Enemy.class: Enemy.java
	javac -g Enemy.java

Gun.class: Gun.java
	javac -g Gun.java

LCU.class: LCU.java
	javac -g LCU.java

Main.class: Main.java Barrier.class Bomb.class Bullet.class Enemy.class Gun.class
	javac -g Main.java

clean:
	rm -f *.class