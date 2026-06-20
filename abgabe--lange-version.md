# Wie unterscheiden sich Type Classes aus Haskell von Interfaces aus Java?

## Intro

In der heutigen Programmierwelt spielen vor allem objektorientierte Programmiersprachen eine Rolle. Betrachtet man so zum Beispiel die jährlich durchgeführte "Developer Survey" von Stack Overflow und deren Ergebnisse aus dem Jahr 2025, zählt neben den üblichen Websprachen auch Java zu einer der beliebtesten Sprachen in der Programmierung. Funktionale Sprachen, wie Haskell, erscheinen in den Umfragen eher am unteren Ende oder tauchen gar nicht in den Graphen auf. Dabei können funktionale Sprachen in manchen Fällen nützlicher sein als objektorientierte Sprachen. [^stackoverflow]

Funktionale Sprachen basieren in erster Linie auf der Verkettung von mathematischen Funktionen, ein Programm entspricht dabei der Menge von Funktionsdefinitionen. Während in objektorientierten Sprache dem Rechner gesagt wird, was zu tun ist, wird bei einer funktionalen Sprache nur definiert, was etwas ist. Ein Programm ist eine Liste von Definitionen, nicht von Anweisungen. Eine Unterkategorie der funktionalen Sprachen sind die puren Sprachen. Bei der Nutzung einer solchen Sprache gibt es keine Zustände und meistens keine Nebeneffekte. Werte können nach der Definition nicht mehr verändert werden. Ein bekanntes Beispiel für eine solche Sprache ist Haskell. [^goetheuniskript][^programmierparadigmen]

## Was ist Haskell?

In den 1980er Jahren wurde beschlossen, dass man eine neue funktionale Sprache entwickeln wollte, welche als standardisierte Grundlage für die Forschung mit funktionalen Sprachen dienen sollte. Als Grundlage wurde die Sprache "Miranda" genutzt und in den 1990ern erste Versionen von Haskell entwickelt. Damals wurde auch das später betrachtete Feature der Type Classes bereits hinzugefügt. In 1998 wurde Haskell 98 definiert, welches für Standard Haskell Compiler genutzt wurde. In 2010 wurde eine überarbeitete Version von Haskell veröffentlicht, welche Haskell 98 ersetzte. [^practicalhaskell]

Haskell ist eine pure funktionale Programmiersprache und ist stark sowie statisch typisiert. D.h. neben den Eigenschaften einer funktionalen Programmiersprache wird zwischen den Datentypen streng unterschieden und die Typen stehen zum Zeitpunkt der Übersetzung bereits fest. Der Compiler überprüft die Typen bevor diese ausgeführt werden. Sollte kein Typ explizit angegeben sein, wird der Typ automatisch aus dem Code abgeleitet. Zudem wird Haskell-Code auch "lazy" evaluiert, d.h. ein Ausdruck wird erst dann ausgewertet, wenn er gebraucht wird, und sein Wert wird gespeichert. Haskell kann zudem auch impure Funktionen, also Funktionen mit Nebeneffekten, unterstützen. Dabei muss der Nebeneffekt beim Funktionstypen mit angegeben werden. [^practicalhaskell][^programmierparadigmen]

Ein einfaches Beispiel für Haskell-Code ist das klassische "Hello World"-Programm.

```haskell
main :: IO()
main = putStrLn "Hello World!"	
```

Die erste Zeile ist dabei der Funktionstyp. Die Funktion "main" liefert dementsprechend eine Konsolenausgabe. In der zweiten Zeile erfolgt die Ausgabe via "putStrLn".
Zum Vergleich, das gleiche "Hello World"-Programm würde in Java wie folgt aussehen:

```Java
public class HelloWorld {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
```
Anders als in Java werden in Haskell auch keine Semikolons zum Abschließen von Ausdrücken benötigt. Es muss auch keine Klasse oder ähnliches definiert werden. Die "main"-Funktion wird, wie die gleiche Funktion in Java, automatisch ausgeführt. 

Definitionen von Funktionen in Haskell haben keinen klassischen Funktionskörper (geschweifte Klammern). Ein einfaches Beispiel für das Multiplizieren von zwei Zahlen kann im Folgenden betrachtet werden.
```haskell
multiply :: Int -> Int -> Int
multiply x y = x * y
```
In der ersten Zeile steht wieder der Funktionstyp der Funktion "multiply". Die Funktion nimmt eine Int, eine weitere Int und gibt eine Int zurück. In Haskell kann eine Funktion immer nur einen Parameter auf einmal entgegen nehmen, weshalb die Übergabe von Parametern in Schritten läuft. Dabei werden die Parameter schrittweise in die Funktion eingesetzt und die dabei entstehenden "neuen" Funktionen jeweils zurückgegeben bis die Parameter vollständig eingesetzt wurden. Im Beispiel bedeutet das: Zuerst nimmt "multiply" den Parameter "x" entgegen, setzt diesen in "multiply" ein und gibt das "multiply" mit eingesetztem "x" weiter. Diese Funktion nimmt dann "y" entgegen und setzt es ein und gibt den Wert zurück:
```
multiply 1 2
	1) multiply 1 => 1 * y = multiply'
	2) multiply' 2 => 1 * 2 = 2  
```
Diese Eigenschaft wird auch als Currying bezeichnet und man spricht von einer partiellen Anwendung der Funktion. 

Zum Vergleich, in Java würde die Definition der "multiply"-Funktion wie folgt aussehen:

```Java
public int multiply(int x, int y) {
	return x * y;
}
```

Eine weitere Eigenschaft von Haskell ist das Anlegen von benutzerdefinierten Datentypen. Um einen solchen Typen anzulegen, wird das Keyword "data" genutzt, gefolgt von dem Namen und dem Konstruktor. Ein Datentyp kann mehrere Konstruktoren haben, so ist folgendes Beispiel möglich:

```haskell
data Shape = Square Int | Triangle Int Int Int | Circle Int
```

Der Datentyp "Shape" kann entweder ein "Square", "Triangle" oder "Circle" sein. Je nach Fall ist der Konstruktor unterschiedlich und nimmt andere Parameter entgegen. 

## Was sind Type Classes in Haskell?

Eine weitere Besonderheit von Haskell sind Type Classes. Mit ihnen lassen sich Gruppen von Methoden definieren, die von mehreren Datentypen genutzt werden können. Die Datentypen gehören dann zu der jeweiligen Type Class, wenn sie die Methoden der Type Class implementieren. Jede Methode ist dabei unterschiedlich je nach Typ.[^haskelltypeclasses]
Um eine Type Class zu definieren, wird das Keyword "class" verwendet, gefolgt von dem Namen, einer Variable sowie dem Keyword "where". Ein Beispiel wäre die Type Class "NumeralSystem", wie folgend gezeigt:

```haskell
class NumeralSystem a where
  convertNumber :: a -> Int -> String
  displaySystem :: a -> IO()
```

Die Type Class "NumeralSystem" gruppiert Methoden für Typen, welche ein Zahlensystem repräsentieren. Funktionen, die implementiert werden müssen, um zur Type Class zu gehören, sind eine Methode zum Konvertieren einer Int in das Zahlensystem sowie eine Methode, um Informationen zum Zahlensystem anzeigen zu lassen. 
Um eine Implementierung einer Type Class zu erstellen, muss man eine Instanz für einen Datentypen anlegen. Dies erfolgt über das Keyword "instance", wie am Beispiel "Binary" gezeigt wird.

```haskell
data Binary = Binary { details :: String }
instance NumeralSystem Binary where
  convertNumber system x
    | x < 2 = show x
    | otherwise = convertNumber system (x `div` 2) ++ show (x `mod` 2)
  displaySystem system = do 
    putStrLn "System Name: Binary"
    putStrLn $ "Details: " ++ details system
```

Zunächst wird der Datentyp "Binary" für das binäre Zahlensystem definiert. Dieser besitzt eine Eigenschaft "details". Anschließend wird die Instanz der Type Class für "Binary" erstellt, bei der für jede Methode der Type Class eine Implementierung angegeben wird. In der Methode "convertNumbers" werden zudem "Guards" genutzt, mit dem der Fall abgefangen wird, bei dem die aktuelle Ziffer den Wert 1 oder 0 hat.  Guards funktionieren im Prinzip wie ein Switch-Case von anderen Programmiersprachen. 
Type Classes werden hauptsächlich für die Überladung von Methoden genutzt. Eine Type Class kann aber zudem auch die Superklasse einer anderen Type Class sein. Jede Instanz der Type Class ist dann auch eine Instanz der Superklasse. Um eine solche Klasse zu definieren, fügt man der Type Class einen Constraint hinzu:

```haskell
class NumeralSystem a => Validator a where
  checkIfValid :: a -> String -> Bool

instance Validator Binary where
  checkIfValid _ [] = True
  checkIfValid system (x:xs)
    | x == '0' || x == '1' = checkIfValid system xs
    | otherwise = False
```

Die Type Class "Validator" hat die Klasse "NumeralSystem" als Superklasse. Dies wird durch den Constraint "NumeralSystem a =>" bei der Klassendefinition angegeben. Die Methode der Type Class überprüft, ob ein String den Regeln des Zahlensystems entspricht. Für "Binary" wird dementsprechend geprüft, ob der String nur aus 0 und 1 besteht. Dafür wird der String als Liste interpretiert. Dabei beschreibt "x" den Head und "xs" den Tail der Liste. Wenn "x" entweder 0 oder 1 entspricht, wird die Methode rekursiv mit dem Tail aufgerufen, bis die Liste entweder leer ist (=> String ist valide) oder "x" nicht 0 oder 1 entspricht (=> String ist nicht valide).
Das vollständige Type Classes Beispiel kann im [Haskell Playground](https://play.haskell.org/saved/4OQ1vrIy) betrachtet und ausgeführt werden.

<br><br>
## Wie unterscheiden sich Type Classes (Haskell) von Interfaces (Java)?

Interfaces aus Java lassen sich in etwa mit Type Classes vergleichen. Eine Klasse in Java, welche die Implementierung eines Interfaces sein kann, entspricht dabei ebenfalls in etwa einer Instanz einer Type Class in Haskell. Es gibt jedoch wichtige Unterschiede, auf die im Folgenden eingegangen werden soll. 
Zunächst sollen die syntaktischen Unterschiede betrachtet werden. Im Folgenden wird deswegen das Beispiel des "NumeralSystem" übersetzt in Java gezeigt:
```Java
// NumeralSystem.java
public interface NumeralSystem {
	String convertNumber(int number);
	void displaySystem();
}

// Binary.java
public class Binary implements NumeralSystem {
	String details;  
	  
	Binary (String details) {  
	    this.details = details;  
	}  
	  
	@Override  
	public String convertNumber (int number) {  
	    return Integer.toBinaryString(number);  
	}  
	  
	@Override  
	public void displaySystem () {  
	    System.out.println("System Name: Binary");  
	    System.out.println("Details: " + this.details);  
	}
}
```
Neben den Unterschieden in den Methodensignaturen und den allgemeinen Unterschieden zwischen Java und Haskell (z.B.: Semikolon am Ende einer Zeile) fällt auf, dass in Haskell explizit der Typ "a" übergeben werden muss. Durch die Objektorientierung kann in Java darauf verzichtet werden und stattdessen auf die Eigenschaften mit "this" direkt in der Methode zugegriffen werden. Dies lässt sich zum Beispiel beim Setzen von "details" im Konstruktor von Binary beobachten. 
Zudem werden in Java Methoden und Daten innerhalb einer Struktur (Klasse) definiert, während die Definition in Haskell in zwei Strukturen getrennt wird ("data" und "instance"). Im Haskell-Beispiel von "NumeralSystem" wird so der Typ "Binary" und die dazugehörige Instanz mit der Implementierung der Methoden definiert. Im Java-Beispiel wird beides durch die Klasse "Binary" definiert.

Durch das Betrachten des Beispiels in Java fällt ebenfalls auf, dass die Instanz einer Klasse einem Objekt entspricht (Objektorientierung). Dementsprechend beschreibt ein Interface den Aufbau und das Verhalten eines Objektes. In Haskell gibt es keine Objektorientierung und dementsprechend auch keine Objekte. Stattdessen beschreibt eine Instanz das Verhalten eines Typen und eine Type Class eine Gruppe von Typen mit gleichen Methoden. Dies fällt vor allem dann auf, wenn man sich die Aufrufe der beiden Klassen anschaut:
```haskell
-- Haskell
let sys = Binary { details = "Base 2 number system" }
putStrLn (convertNumber sys 42)
```

```java
// Java
Binary b1 = new Binary ("Base 2 number system");
System.out.println(b1.convertNumber(42));
```
In Java erstellen wir ein neues Objekt mit Methoden, während wir in Haskell nur einen Datencontainer anlegen. Der Methodenaufruf in Haskell erfolgt nicht wie bei Java über die Variable (bzw. das Objekt), sondern mit der Variable. Anhand ihres Typs wird dann entschieden, welche Methode aufgerufen wird. 
Zudem werden Interfaces in Java auch oft als Schnittstelle betrachtet. Sie regeln die Kommunikation ihrer Klassen zu anderen Interfaces und deren Klassen. Die Aufgabe von Type Classes ist nicht die Kommunikation, sondern die Gruppierung der Typen.

Ein weiterer Unterschied ist der Zeitpunkt, an dem entschieden wird, welche Methode oder Instanz ausgeführt wird. In Haskell wird die Entscheidung, welche Implementierung der Type Class ausgeführt wird, bei der Kompilierung getroffen (➝"static dispatch"). Die Entscheidung wird dabei anhand des Typen getroffen, da der Compiler schon vor der Ausführung den Typen kennt und so die entsprechende Methode einsetzen kann. In Java hingegen erfolgt die Entscheidung erst zur Laufzeit anhand des Objektes, über das die Methode aufgerufen wird (➝"dynamic dispatch"). Eine Folge davon ist, dass zum Beispiel bei der Verwendung des Interface-Typen als Parameter- und Rückgabe-Typ keine Typsicherheit garantiert ist. Ein solcher Fall wird im folgenden Beispiel gezeigt:
```Java
// Numbers.java
public interface Numbers {
	Numbers subtract(Numbers subtrahend);
}

// Decimal.java
public class Decimal implements Numbers {
	String value;  
	  
	Decimal (String value) {  
	    this.value = value;  
	}  
	  
	@Override  
	public Numbers subtract (Numbers subtrahend) {
		Decimal sub = (Decimal) subtrahend;
		int diff = Integer.parseInt(this.value) - Integer.parseInt(sub.value);  
		return new ChaosNumber(Integer.toString(diff));
	}
}

// ChaosNumber.java
public class ChaosNumber implements Numbers {
	String value;
	
	ChaosNumber (String value) {
		this.value = value;
	}
	  
	@Override  
	public Numbers subtract (Numbers subtrahend) {
		// ...
	}
}
```
Das Interface "Numbers" hat eine Methode "subtract", welche ein Numbers-Objekt als Parameter entgegen nimmt und ein Objekt zurückgibt. Theoretisch kann hier jedes beliebige Objekt eingesetzt werden, welches das Interface implementiert. Der Compiler weiß allerdings nicht, welche Implementierung genutzt wird und ob sie vom gleichen Typ ist. So kann der Fall eintreten, dass Objekte einer anderen Klasse an eine solche Methode übergeben werden. In der Theorie ist das möglich, da beide Klassen "Numbers" implementieren und als Übergabewert ein Objekt des Typs "Numbers" empfangen wird. Durch die Manipulation, in unserem Beispiel das Subtrahieren voneinander, müssen beide Objekte allerdings von der selben Klasse stammen. Dies kann jedoch nicht garantiert werden. Um dies zu verhindern und Typsicherheit zu garantieren, muss ein solches Objekt, welches den Interface-Typ hat, zuerst entsprechend gecastet werden, wie man ebenfalls im Beispiel sehen kann. In Haskell kommt es aufgrund des "static dispatch" nicht zu solchen Problemen. Der Compiler erkennt anhand des Typen die richtige Methode und garantiert dabei, dass die Parameter den gleichen Typen haben.

Eine Gemeinsamkeit beider Features ist die Nutzung von Superklassen. Type Classes und Interfaces können durch eine andere Type Class oder Interface erweitert werden. Auch hierbei müssen dann alle Methoden implementiert werden. Ebenfalls können beide Strukturen nachträglich zu einer Klasse oder einem Datentypen hinzugefügt werden. Dabei gibt es jedoch eine weitere Besonderheit in Java. In der Klassendefinition muss angegeben werden, ob und welches Interface implementiert wird und alle zu implementierenden Methoden müssen in der Klasse implementiert werden. Beim nachträglichen Hinzufügen eines weiteren Interfaces muss die ursprüngliche Klasse dabei bearbeitet und angepasst werden. Die ursprüngliche Version der Klasse wird dabei ersetzt. Bei Type Classes in Haskell wird eine separate neue Instance für jede Type Class angelegt, sodass die ursprüngliche Instance erhalten bleibt.

## Erkenntnisse (Zusammenfassung)
|                                    | Haskell                                                                                 | Java                                                                                     |
| ---------------------------------- | --------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| Zweck                              | Gruppierung von Methoden für Datentypen                                                 | Kommunikation zwischen Klassen                                                           |
| Zugriff auf Methoden               | Typ muss explizit übergeben werden, Aufruf erfolgt über den Typen                       | über "this" direkter Zugriff auf die Methoden (durch Objektorientierung)                 |
| Definition von Methoden            | In der Instanz der Type Class für den Datentypen                                        | In der Klasse                                                                            |
| Definition von Daten               | In der Definition des Datentyps                                                         | In der Klasse                                                                            |
| Was beschrieben wird               | Type Class = Gruppe von Typen mit gleichen Methoden<br>Instance = Verhalten eines Typen | Interface = Aufbau und Verhalten eines Objekts<br>Klasse = Objekt                        |
| Zeitpunkt der Methodenentscheidung | Kompilierung (static dispatch)                                                          | Laufzeit (dynamic dispatch)                                                              |
| Entscheidungskriterium             | Typ                                                                                     | Objekt                                                                                   |
| Typsicherheit                      | Durch Compiler garantiert                                                               | Kann nicht garantiert werden (möglich durch z.B. Casting)                                |
| Superklassen                       | möglich                                                                                 | möglich                                                                                  |
| Nachträgliche Erweiterung          | Ergänzung einer weiteren Instance ohne Veränderung der ursprünglichen Instanz           | Bearbeitung und Anpassung der ursprünglichen Klasse (Ersetzen der ursprünglichen Klasse) |
<br><br>

[^goetheuniskript]: Schmidt-Schauß, M. (2023): _Einführung in die Funktionale Programmierung_, Vorlesungsskript. Goethe-Universität Frankfurt. [https://www2.ki.informatik.uni-frankfurt.de/lehre/WS2025/EFP/skript/skript.pdf](https://www2.ki.informatik.uni-frankfurt.de/lehre/WS2025/EFP/skript/skript.pdf) [abgerufen am: 05.06.2026].
[^stackoverflow]: Stack Overflow (2025): _2025 Developer Survey: Most popular technologies (Language)_. [https://survey.stackoverflow.co/2025/technology#most-popular-technologies-language-language](https://survey.stackoverflow.co/2025/technology#most-popular-technologies-language-language) [abgerufen am: 05.06.2026].
[^programmierparadigmen]:  Sulzmann, M. (2026): _Programming Paradigms - Haskell_, Vorlesungsmaterial. Hochschule Karlsruhe. https://sulzmann.github.io/ProgrammingParadigms/pp-haskell.html#(1) [abgerufen am 05.06.2026].
[^practicalhaskell]: Serrano Mena, A. (2022): _Going Functional_. In: Practical Haskell. Apress, Berkeley, CA.  https://doi.org/10.1007/978-1-4842-8581-7_1 [abgerufen am 05.06.2026].
[^haskelltypeclasses]: Dreimanis, G. (2022): _Introduction to Haskell Typeclasses_. https://serokell.io/blog/haskell-typeclasses [abgerufen am 01.06.2026]
