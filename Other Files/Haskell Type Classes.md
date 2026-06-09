# Haskell Type Clases

Mit Type Classes kann man Funktionen leichter überladen, in dem man eine Type Class Constraint an die Methodendefinition anhängt. Damit ein Typ zu einer Type Class gehört, muss er die Methoden der Type Class implementieren, wobei sich die Methoden je nach Typ unterscheiden können. 

## Aufbau
Eine Type Class beinhaltet die Signaturen der Methoden, die eine Instanz implementieren muss, um zur Class zu gehören. Um eine Type Class zu definieren, wird das Keyword *class* genutzt. Neben einem Namen und einem Typen müssen auch die Methoden-Signaturen angegeben werden. Das folgende Beispiel zeigt die Type Class *Shape* mit dem Typen a:
```haskell
class Shape a where
	area :: a -> Int
```
## Instanzen / Implementierung
Die Instanz einer Type Class entspricht der Implementierung der Methoden für einen bestimmten Typen. Hierfür wird das Keyword *instance* genutzt. Alle Methoden einer Type Class müssen implementiert werden. Die Implementierung kann je nach Typ unterschiedlich aussehen. Ein Beispiel ist die Instanz der Type Class *Shape* für den Typ *Square*, wie folgend gezeigt:
```haskell
data Square = Square Int
instance Shape Square where
	area (Square x) = x * x
```
Zunächst wird der Datentyp *Square* definiert: Der Konstruktor nimmt eine Int als Argument, die der Seitenlänge entspricht. Es wird anschließend eine Instanz für *Shape* mit dem Typ *Square* definiert mit der Methode *area* von *Shape*. 
## Verwendung
Type Classes werden vor allem für das Überladen von Funktionen genutzt. Zudem können Type Classes auch als Superklassen genutzt werden (siehe Type Classes als Super Class). 
## Beispiel
```haskell
class Measuring a where
	getInMl :: a -> Int
	
data Spoon = Spoon Int
data Bottle = Bottle {amount :: Int, litre :: Int}	

instance Measuring Spoon where
	getInMl (Spoon x) = x * 15
	
instance Measuring Bottle where
	getInMl y = amount y * litre y * 1000

measuringExample = getInMl s + getInMl b
	where
		s = Spoon 4
		b = Bottle {amount = 2, litre = 2}
```
Das Beispiel zeigt eine selbstdefinierte Type Class *Measuring* mit der überladenen Methode *getInMl*, welche die Menge in Milliliter zurückgeben soll. Die Typen *Spoon* und *Bottle* nutzen beide *Measuring.*  *Spoon* entspricht dabei einem Kochlöffel, seine Int beschreibt die Anzahl der Löffel. *Bottle* nutzt Labels um auf Komponenten zu verweisen, hier auf die Menge an Flaschen und die Liter pro Flasche. Für beide Typen gibt es eine Instanz der *getInMl* Methode, welche entsprechend die Milliliter-Menge berechnet. 

## Listen und Type Classes
Man kann Type Classes auch für Listen verwenden. Dabei müssen alle Element ebenfalls Instanzen der Type Class sein:
```haskell
data List t = Nil | Cons t (List t) 
instance Measuring a => Measuring (List a) where
  getInMl Nil = 0
  getInMl (Cons x xs) = getInMl x + getInMl xs

listExample = getInMl l
  where
    l = Cons (Spoon 2) (Cons (Spoon 3) Nil)
```
Da der Compiler weiß, welchen Typ er gerade behandelt, wird bei der Liste automatisch die *getInMl* Funktion für den jeweiligen Typen aufgerufen. 
Das Beispiel nutzt einen generic Datentypen und braucht deswegen den Constraint; a muss vom Typ Measuring sein, damit wir eine Liste der Type Class Measuring mit Elementen vom Typ a anlegen können.

## Type Classes als Super Class
Type Classes können auch als Superklasse genutzt werden für andere Klassen. Hierfür wird die Superklasse als Constraint gesetzt:
```haskell
class Ingredient i where
  density :: i -> Double
  
data Water = Water
data Sugar = Sugar

instance Ingredient Water where density _ = 1.0
instance Ingredient Sugar where density _ = 0.845

class Measuring a => ExtendedMeasuring a where
  getInMg :: Ingredient i => a -> i -> Int

data SpoonExt = SpoonExt Int

instance Measuring SpoonExt where
  getInMl (SpoonExt x) = x * 15

instance ExtendedMeasuring SpoonExt where
  getInMg (SpoonExt x) i = x * 15 * round(density i * 1000)

superClassExample = getInMg s i
  where
    s = SpoonExt 4
    i = Sugar
```
Dabei hat *ExtendedMeasuring* nicht nur die *getInMg* Funktion sondern auch die Methode *getInMl* von Measuring.


Vollständiges "Measuring"-Beispiel: [Playground](https://play.haskell.org/saved/Ju6qjt1f) 
