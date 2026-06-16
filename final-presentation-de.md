# Wie unterscheiden sich Type Classes aus Haskell von Interfaces aus Java?
## Intro
<figure>
  <img width="1235" height="997" alt="Stack Overflow Developer Survey 2025 - Most popular technologies" src="https://github.com/user-attachments/assets/966d4054-02a0-4ba6-b1df-39556cf38550">
  <figcaption>Stack Overflow Developer Survey 2025 - Most popular technologies (gekürzt) (https://survey.stackoverflow.co/2025/technology#most-popular-technologies-language-language)</figcaption>
</figure>

<br><br>

<p align="center" width="100%">
    <img width="80%" src="https://github.com/user-attachments/assets/aea730c0-7756-41a1-a010-cc7fa77ef4aa">
</p>

<br><br>

## Was ist Haskell? 
- 1990er erste Versionen
- 1998 Haskell 98
- pure funktionale Programmiersprache
- stark und statisch typisiert
- lazy evaluiert
### Einfaches Beispiel
```haskell
-- Haskell
main :: IO()
main = putStrLn "Hello World!"	
```

```Java
// Java
public class HelloWorld {
	public static void main(String[] args) {
		System.out.println("Hello World!");
	}
}
```

### Funktionen
```haskell
-- Haskell
multiply :: Int -> Int -> Int
multiply x y = x * y
```
```
multiply 1 2
	1) multiply 1 => 1 * y = multiply'
	2) multiply' 2 => 1 * 2 = 2  
```
=> Currying

```Java
// Java
public int multiply(int x, int y) {
	return x * y;
}
```

### Datentypen
```haskell
data Shape = Square Int | Triangle Int Int Int | Circle Int
```

<br><br>
## Was sind Type Classes in Haskell?
- Gruppen von Methoden für mehrere Datentypen (Überladung von Methoden)
- Unterschiedliche Methoden je nach Typ
- Keyword "class" für Type Class, "instance" für Datentyp Instanz

```haskell
class NumeralSystem a where
  convertNumber :: a -> Int -> String
  displaySystem :: a -> IO()
```

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

- Superklassen über Constraints möglich
```haskell
class NumeralSystem a => Validator a where
  checkIfValid :: a -> String -> Bool

instance Validator Binary where
  checkIfValid _ [] = True
  checkIfValid system (x:xs)
    | x == '0' || x == '1' = checkIfValid system xs
    | otherwise = False
```

<br><br>
## Wie unterscheiden sich Type Classes (Haskell) von Interfaces (Java)?
- lassen sich in etwa vergleichen
- Interface = Type Class, Klasse = Instance

```Java
// Numeral System in Java
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

|                                     | Haskell                                                                                 | Java                                                                                     |
| ----------------------------------- | --------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| Zugriff auf Methoden                | Typ muss explizit übergeben werden, Aufruf erfolgt über den Typen                       | über "this" direkter Zugriff auf die Methoden (durch Objektorientierung)                 |
| Definition von Methoden             | In der Instanz der Type Class für den Datentypen                                        | In der Klasse                                                                            |
| Definition von Daten                | In der Definition des Datentyps                                                         | In der Klasse                                                                            |
| Was beschrieben wird                | Type Class = Gruppe von Typen mit gleichen Methoden<br>Instance = Verhalten eines Typen | Interface = Aufbau und Verhalten eines Objekts<br>Klasse = Objekt                        |
| Aufgabe                             | Gruppierung Typen                                                                       | Kommunikation                                                                            |
| Zeitpunkt der Methodenentscheidung  | Kompilierung (static dispatch)                                                          | Laufzeit (dynamic dispatch)                                                              |
| Entscheidungskriterium              | Typ                                                                                     | Objekt                                                                                   |
| Typsicherheit                       | Durch Compiler garantiert                                                               | Kann nicht garantiert werden (möglich durch z.B. Casting)                                |
| Superklassen                        | möglich                                                                                 | möglich                                                                                  |
| Ergänzung von weiteren Superklassen | Ergänzung einer weiteren Instance ohne Veränderung der ursprünglichen Instanz           | Bearbeitung und Anpassung der ursprünglichen Klasse (Ersetzen der ursprünglichen Klasse) |

```haskell
-- Haskell Methoden Aufruf
let sys = Binary { details = "Base 2 number system" }
putStrLn (convertNumber sys 42)
```
```java
// Java Methoden Aufruf
Binary b1 = new Binary ("Base 2 number system");
System.out.println(b1.convertNumber(42));
```

```Java
// Typsicherheit Beispiel

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

<br><br>
## Erkenntnisse (Zusammenfassung)
- Haskell ist eine pure funktionale Programmiersprache ohne Objektorientierung.
- Type Classes sind Gruppierungen von Typen mit gleichen Methoden.
- Type Classes haben Ähnlichkeiten zu Interfaces von Java.
- In Java werden Methoden und Daten in der gleichen Struktur definiert (Klasse), in Haskell in zwei unterschiedlichen (Data und Instance).
- Die Instanz einer Klasse in Java ist ein Objekt, eine Instanz einer Type Class in Haskell ist ein Typ.
- Die Aufgabe eines Interfaces in Java ist die Kommunikation mit anderen Klassen. Eine Type Class gruppiert Typen.
- In Haskell wird die Entscheidung, welche Implementierung genutzt wird, während der Kompilierung getroffen (➝ "static dispatch"), während in Java die Entscheidung während der Laufzeit getroffen wird (➝"dynamic dispatch").  
- Als Folge des "dynamic dispatch" in Kombination mit Subtyping kann unter Umständen keine Typsicherheit durch den Compiler garantiert werden.
- Das nachträgliche Hinzufügen eines Interfaces zu einer Klasse in Java ersetzt die ursprüngliche Version, während in Haskell die ursprüngliche Version ergänzt wird.

<br><br>
## Quellen
### Haskell
- Sulzmann, M. (2026): _Programming Paradigms - Haskell_, Vorlesungsmaterial. Hochschule Karlsruhe. https://sulzmann.github.io/ProgrammingParadigms/pp-haskell.html#(1) [abgerufen am 05.06.2026].
- Dreimanis, G. (2022): _Introduction to Haskell Typeclasses_. https://serokell.io/blog/haskell-typeclasses [abgerufen am 01.06.2026]
- Serrano Mena, A. (2022): _Going Functional_. In: Practical Haskell. Apress, Berkeley, CA.  https://doi.org/10.1007/978-1-4842-8581-7_1 [abgerufen am 05.06.2026].
### Java
- Knauber, P. (2019): _Programmieren 1 - 50 Interfaces und Lineare Listen_, Vorlesungsfolien. Technische Hochschule Mannheim.
- Oracle (2024): _What Is an Interface?_. https://docs.oracle.com/javase/tutorial/java/concepts/interface.html [abgerufen am 09.06.2026]
- Babayev, K. (2023): _Interface in Java_. https://medium.com/@kamran.babayevv/interface-in-java-e3f63738f730 [abgerufen am 09.06.2026]
### Andere
- Stack Overflow (2025): _2025 Developer Survey: Most popular technologies (Language)_. [https://survey.stackoverflow.co/2025/technology#most-popular-technologies-language-language](https://survey.stackoverflow.co/2025/technology#most-popular-technologies-language-language) [abgerufen am: 05.06.2026].
- Schmidt-Schauß, M. (2023): _Einführung in die Funktionale Programmierung_, Vorlesungsskript. Goethe-Universität Frankfurt. [https://www2.ki.informatik.uni-frankfurt.de/lehre/WS2025/EFP/skript/skript.pdf](https://www2.ki.informatik.uni-frankfurt.de/lehre/WS2025/EFP/skript/skript.pdf) [abgerufen am: 05.06.2026].
