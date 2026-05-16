# Java Interfaces 

Ein Interface in Java kann als eine Schnittstelle zwischen Komponenten, die von Klassen zur Kommunikation untereinander genutzt werden, betrachtet werden. In Interfaces wird nur festgehalten, was eine Klasse, die das Interface implementiert, kann, aber nicht wie es implementiert wird. Ein gutes reelles Beispiel für das bessere Verständnis ist die Kopfhörerbüchse eines Handys. Der Kopfhörer muss nicht wissen, wie das Handy seine Daten empfängt. Er muss nur wissen, dass es das kann. Zudem können Interfaces auch als Typ-Angabe statt einer der Klasse, die das Interface implementiert, genutzt werden. [^knauber]

## Aufbau
Ein Interface besteht aus verschiedenen Methoden-Signaturen, oft mit einer JavaDoc-Beschreibung, sowie ggf. weitere Merkmale in Form von Feldern. In den meisten Fällen sind Methoden eines Interfaces nicht-statische Methoden. Um ein Interface zu deklarieren, wird das Keyword *interface* genutzt. Ein Interface beinhaltet keine Implementierungen.  Das folgende Beispiel zeigt das Interface MP3Player mit den zu implementierenden Methoden.[^knauber]
```java
public interface MP3Player {
	void selectPlaylist(String playlistName);
	void play(int songPos);
	String playNext();
	void stop();
	int setVolume(int volume);
}
```

Seit Java 8 können Interfaces auch statische und default Methoden beinhalten. Default Methoden sind Methoden mit einer Implementierung im Interface. Die Implementierung wird standardmäßig eingesetzt, d.h. bei Klassen, welche die gleiche Implementierung benötigen, muss die Methode nur einmal im Interface implementiert werden und nicht mehrfach in jeder der Klassen. So kann also die unnötige Doppelimplementierung verhindert werden. Um eine Methode als default zu kennzeichnen, muss das Keyword *default* in der Methoden-Signatur genutzt werden und die Methode muss eine Implementierung besitzen.  Die Default-Methoden können auch in den Klassen einfach überschrieben werden. [^mediumarticle]

Private Methoden können seit Java 9 in Interfaces genutzt werden. Da es nicht möglich ist, eine Instanz eines Interfaces zu erstellen, wird dieser Methodentyp wird hauptsächlich für Hilfsmethoden im Interface genutzt. [^mediumarticle]
## Instanzen / Implementierung
Eine Implementierung eines Interfaces ist eine Klasse. Eine Klasse, die ein Interface implementiert, muss alle Methoden implementieren. Jede Methode des Interfaces muss in der Klasse mit dem Zugriffsmodifikator *public* implementiert werden. Wie die Implementierung aussieht, ist von Klasse zu Klasse unterschiedlich (siehe Verwendung ➝ Polymorphie). Die Instanz einer Klasse entspricht einem Objekt. Oft spricht man deswegen auch davon, dass ein Interface das Verhalten eines solchen Objekts beschreibt. Zudem muss eine Klasse nicht zwangsweise nur ein Interface implementieren; die Klasse kann auch mehr als ein Interface implementieren und ein Interface kann durch mehrere Klassen implementiert werden (siehe Verwendung ➝ Multiple Vererbung).[^knauber] [^oracle]
```java
public class IPod implements MP3Player {
	public void selectPlaylist(String playlistName) {
		...
	}
	...
}

public static void main (String[] args) {
	MP3Player iPod = new IPod();
	iPod.selectPlaylist("Beccas Mix");
}
```
Die Klasse *IPod* ist eine Implementierung des Interfaces *MP3Player* . Damit sie als solches erkannt wird, wird nach dem Klassennamen in der Klassen-Signatur *implements MP3Player* angewendet. In der Klasse müssen dann alle Methoden von *MP3Player* als public Methoden implementiert werden. Ist dies erfolgt, kann ein Objekt von der Klasse angelegt werden und die Methoden so aufgerufen werden.
## Verwendung
Interfaces werden in Java vor allem für Polymorphie, Abstraktion und multiple Vererbung genutzt. 

**Polymorphie**: Jede Klasse enthält auf sie spezialisierte Methoden des jeweiligen Interfaces. Mehrere Klassen können so zwar das gleiche Interface nutzen, aber sich trotzdem unterscheiden. Ein Beispiel wäre hier das Interface Num und die Klassen Int und Float. Beide Klassen implementieren das Interface Num, haben aber unterschiedliche Umsetzungen der Methoden des Interfaces.

**Abstraktion**: Im Interface werden nur Methoden-Signaturen genutzt, keine Implementierung. Wir beschreiben also nur was die Implementierungen tun sollen, nicht wie sie sind. 

**Multiple Vererbung**: Eine Klasse kann mehreren Interfaces gleichzeitig implementieren. In so einem Fall müssen die Methoden von allen Interfaces implementiert werden. [^mediumarticle]
## Beispiel
```java
// Measuring.java 
package Measuring;  
  
public interface Measuring {  
    int getInMl();  
}
```

```java
// MeasuringExt.java 
package Measuring;  
  
public interface MeasuringExt {  
    int getInMg();  
}
```

```java
// Spoon.java 
package Measuring;  
  
import static java.lang.Math.round;  
  
public class Spoon implements Measuring, MeasuringExt {  
    private int amount = 0;  
    private float density = 0f;  
  
    public Spoon(int amount, String ingredient) {  
        this.amount = amount;  
        switch (ingredient) {  
            case "Water":  
                this.density = 1.0f;  
                break;  
            case "Sugar":  
                this.density = 0.845f;  
                break;  
            default:  
                break;  
        }  
    }  
  
    public int getInMl() {  
        return this.amount * 15;  
    }  
  
    public int getInMg() {  
        return amount * 15 * round(density * 1000);  
    }  
  
}
```

```java
// Bottle.java 
package Measuring;  
  
public class Bottle implements Measuring {  
    private int amount = 0;  
    private int litrePerBottle = 1;  
  
    public Bottle(int amount, int litrePerBottle) {  
        this.amount = amount;  
        this.litrePerBottle = litrePerBottle;  
    }  
  
    public int getInMl() {  
        return this.amount * this.litrePerBottle * 1000;  
    }  
  
}
```

```java
// Main.java 
package Measuring;  
  
public class Main {  
    public static void main (String[] args) {  
        Spoon spoons = new Spoon(4, "Water" );  
        Bottle bottles = new Bottle(2, 2);  
        System.out.println(spoons.getInMl() + bottles.getInMl());  
        System.out.println(spoons.getInMg());  
    }  
}
```

[^knauber]: Folien zur Vorlesung Programmieren 1 von Prof. Knauber der Technischen Hochschule Mannheim (Stand: Wintersemester 2019/2020) 

[^oracle]: https://docs.oracle.com/javase/tutorial/java/concepts/interface.html (abgerufen: 15.05.2026)

[^mediumarticle]: https://medium.com/@kamran.babayevv/interface-in-java-e3f63738f730 (abgerufen: 15.05.2026)

