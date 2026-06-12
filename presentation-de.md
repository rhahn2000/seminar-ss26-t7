# Wie unterscheiden sich Type Classes aus Haskell von Interfaces aus Java?

-- Stand: 09.06. (unvollständig und ungekürzt) --

## Intro
In der heutigen Programmierwelt spielen vor allem objektorientierte Programmiersprachen eine Rolle. Betrachtet man so zum Beispiel die jährlich durchgeführte "Developer Survey" von Stack Overflow und deren Ergebnisse aus dem Jahr 2025, zählt neben den üblichen Websprachen auch Java zu einer der beliebtesten Sprachen in der Programmierung. Funktionale Sprachen, wie Haskell, erscheinen in den Umfragen eher am unteren Ende oder tauchen gar nicht in den Graphen auf. Dabei können funktionale Sprachen in manchen Fällen nützlicher sein als objektorientierte Sprachen. [^stackoverflow]

Funktionale Sprachen basieren in erster Linie auf der Verkettung von mathematischen Funktionen, ein Programm entspricht dabei der Menge von Funktionsdefinitionen. Während in objektorientierten Sprache dem Rechner gesagt wird, was zu tun ist, wird bei einer funktionalen Sprache nur definiert, was etwas ist. Ein Programm ist eine Liste von Definitionen, nicht von Anweisungen. Eine Unterkategorie der funktionalen Sprachen sind die puren Sprachen. Bei der Nutzung einer solchen Sprache gibt es keine Zustände und meistens keine Nebeneffekte. Werte können nach der Definition nicht mehr verändert werden. Ein bekanntes Beispiel für eine solche Sprache ist Haskell. [^goetheuniskript][^programmierparadigmen]

## Was ist Haskell?
In den 1980er Jahren wurde beschlossen, dass man eine neue funktionale Sprache entwickeln wollte, welche als standardisierte Grundlage für die Forschung mit funktionalen sprachen dienen sollte. Als Grundlage wurde die Sprache Miranda genutzt und in den 1990s erste Versionen von Haskell entwickelt. Damals wurde auch das später betrachtete Feature der Type Classes bereits hinzugefügt. In 1998 wurde Haskell 98 definiert, welches für Standard Haskell Compiler genutzt wurde. In 2010 wurde eine  überarbeitete Version von Haskell veröffentlicht, welche Haskell 98 ersetzte. [^practicalhaskell]

Haskell ist eine pure funktionale Programmiersprache und ist stark sowie statisch typisiert. D.h. neben den Eigenschaften einer funktionalen Programmiersprache wird zwischen den Datentypen streng unterschieden und die Typen zum Zeitpunkt der Übersetzung bereits feststehen. Der Compiler checkt die Typen bevor diese ausgeführt werden. Sollte kein Typ explizit angegeben sein, wird der Typ automatisch aus dem Code abgeleitet. Zudem wird Haskell-Code auch lazy evaluiert, d.h. ein Ausdruck wird erst dann ausgewertet, wenn er gebraucht wird, und sein Wert wird gespeichert. Haskell kann zudem auch impure Funktionen, also Funktionen mit Nebeneffekten. Dabei muss der Nebeneffekt beim Funktionstypen mit angegeben werden. [^practicalhaskell][^programmierparadigmen]

Ein einfaches Beispiel für Haskell-Code ist das klassische "Hello World"-Programm.
```haskell
main :: IO()
main = putStrLn "Hello World!"	
```
Die erste Zeile ist dabei der Funktionstyp. Die Funktion "main" liefert dementsprechend eine Konsolenausgabe. In der zweiten Zeile erfolgt die Ausgabe via "putStrLn".
Zum Vergleich, das selbe "Hello World"-Programm würde in Java wie folgt aussehen.
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
In der ersten Zeile steht wieder der Funktionstyp der Funktion "multiply". Die Funktion nimmt eine Int, eine weitere Int und gibt eine Int zurück. In Haskell kann eine Funktion immer nur einen Parameter auf einmal entgegen nehmen, weshalb die Übergabe von Parametern in Schritten läuft. Dabei werden die Parameter schrittweise in die Funktion eingesetzt und diese "neue" Funktion zurückgegeben. Im Beispiel bedeutet das: Zuerst nimmt "multiply" den Parameter "x" entgegen, setzt diesen in "multiply" ein und gibt das "multiply" mit eingesetztem "x" weiter. Diese Funktion nimmt dann "y" entgegen und setzt es ein und gibt den Wert zurück:
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
Eine weitere Besonderheit von Haskell sind die Type Classes. Mit Type Classes lassen sich Gruppen von Methoden definieren, die von mehreren Datentypen genutzt werden können. Die Datentypen gehören dann zu der jeweiligen Type Class, wenn sie die Methoden der Type Class implementieren. Jede Methode ist dabei unterschiedlich je nach Typ.[^haskelltypeclasses]
Um eine Type Class zu definieren, wird das Keyword "class" verwendet, gefolgt von dem Namen, einer Variable sowie dem Keyword "where". Ein Beispiel wäre die Type Class "NumeralSystem", wie folgend gezeigt:
```haskell
class NumeralSystem a where
  convertNumber :: a -> Int -> String
  displaySystem :: a -> IO()
```
Die Klasse "NumeralSystem" beschreibt Typen, die ein Zahlensystem repräsentieren. Funktionen, die implementiert werden müssen, um zur Type Class zu gehören, ist eine Methode zum Konvertieren einer Dezimalzahl in das Zahlensystem sowie eine Methode, um Informationen zum Zahlensystem anzeigen zu lassen. 
Um einen Implementierung einer Type Class zu erstellen, muss man eine Instanz von ihr für einen Datentypen anlegen. Dies erfolgt über das Keyword "instance", wie folgend am Beispiel "Binary" gezeigt wird.
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
Im Beispiel erstellen wir zunächst den Datentypen "Binary" für das binäre Zahlensystem. Als Eigenschaften besitzt er einen Details-String.  Als nächstes wird die Instanz der Type Class für "Binary" erstellt, bei der für jede Methode der Type Class eine Implementierung angegeben wird. In der Methode "convertNumbers" wird zudem das Feature Guards genutzt, mit dem der Fall abgefangen wird, in dem die Nummer 1 oder 0 ist.  Guards funktionieren im Prinzip wie ein Switch Case. 
Type Classes werden hauptsächlich für die Überladung von Methoden genutzt. Eine Type Class kann aber zudem auch die Super Class einer anderen Type Class sein. Jede Instanz der Type Class ist dann auch eine Instanz der Super Class. Um eine Super Class zu definieren, fügt man der Type Class einen Constraint hinzu:
```haskell
class NumeralSystem a => Validator a where
  checkIfValid :: a -> String -> Bool

instance Validator Binary where
  checkIfValid _ [] = True
  checkIfValid system (x:xs)
    | x == '0' || x == '1' = checkIfValid system xs
    | otherwise = False
```
Die Type Class "Validator" hat die Klasse "NumeralSystem" als Super Class. Dies wird durch den Constraint "NumeralSystem a =>" bei der Klassendefinition angegeben. Die Methode der Type Class überprüft, ob ein String den Regeln des Zahlensystems entspricht. Für Binary wird dementsprechend geprüft, ob der String nur aus 0 und 1 besteht. Dafür wird der String als Liste interpretiert. Dabei beschreibt x den Head und xs den Tail der Liste. Wenn x entweder 0 oder 1 entspricht, wird die Methode rekursiv mit dem Tail aufgerufen, bis die Liste entweder leer ist (=> String ist valide) oder x nicht 0 oder 1 entspricht (=> String ist nicht valide).  
Das vollständige Type Classes Beispiel kann im [Haskell Playground](https://play.haskell.org/saved/4OQ1vrIy) betrachtet und ausgeführt werden.

## Wie unterscheiden sich Type Classes (Haskell) von Interfaces (Java)?
➝ TODO

## Quellen
[^goetheuniskript]: Schmidt-Schauß, M. (2023): _Einführung in die Funktionale Programmierung_, Vorlesungsskript. Goethe-Universität Frankfurt. [https://www2.ki.informatik.uni-frankfurt.de/lehre/WS2025/EFP/skript/skript.pdf](https://www2.ki.informatik.uni-frankfurt.de/lehre/WS2025/EFP/skript/skript.pdf) [abgerufen am: 05.06.2026].
[^stackoverflow]: Stack Overflow (2025): _2025 Developer Survey: Most popular technologies (Language)_. [https://survey.stackoverflow.co/2025/technology#most-popular-technologies-language-language](https://survey.stackoverflow.co/2025/technology#most-popular-technologies-language-language) [abgerufen am: 05.06.2026].
[^programmierparadigmen]:  Sulzmann, M. (2026): _Programming Paradigms - Haskell_, Vorlesungsmaterial. Hochschule Karlsruhe. https://sulzmann.github.io/ProgrammingParadigms/pp-haskell.html#(1) [abgerufen am 05.06.2026].
[^practicalhaskell]: Serrano Mena, A. (2022): _Going Functional_. In: Practical Haskell. Apress, Berkeley, CA.  https://doi.org/10.1007/978-1-4842-8581-7_1 [abgerufen am 05.06.2026].
[^haskelltypeclasses]: Dreimanis, G. (2022): _Introduction to Haskell Typeclasses_. https://serokell.io/blog/haskell-typeclasses [abgerufen am 01.06.2026]
