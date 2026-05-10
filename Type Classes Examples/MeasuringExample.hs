main :: IO ()
main = do putStrLn $ show measuringExample ++ " ml"
          putStrLn "--- list examples ---"
          let l1 = Cons (Spoon 2) (Cons (Spoon 3) Nil)
          putStrLn $ "Spoons in ml: " ++ show (getInMl l1) ++ " ml"
          let l2 = Cons (Bottle {amount = 1, litre = 1}) (Cons (Bottle {amount = 5, litre = 2}) Nil)
          putStrLn $ "Bottles in ml: " ++ show (getInMl l2) ++ " ml"
          putStrLn "--- super class examples ---"
          let amount  = 4
          let s = SpoonExt 4
          let resultSugar = getInMg (s) Sugar
          let resultWater = getInMg (s) Water
          putStrLn $ "Spoons in mg (Sugar): " ++ show resultSugar ++ " mg"
          putStrLn $ "Spoons in mg (Water): " ++ show resultWater ++ " mg"
          putStrLn $ "Spoons in mg (Combined): " ++ show (resultWater + resultSugar) ++ " mg"
          putStrLn $ "Spoons in ml: " ++ show (getInMl s) ++ " ml"



-- type class measuring --
class Measuring a where
  getInMl :: a -> Int

data Spoon = Spoon Int
data Bottle = Bottle {amount :: Int, litre :: Int}

instance Measuring Spoon where
  getInMl (Spoon x) = x * 15

instance Measuring Bottle where
  getInMl y = amount y * litre y * 1000



-- type class ingredient --
class Ingredient i where
  density :: i -> Double
  
data Water = Water
data Sugar = Sugar

instance Ingredient Water where density _ = 1.0
instance Ingredient Sugar where density _ = 0.845



-- Example of using measuring method getInML & type class instances --
measuringExample = getInMl s + getInMl b
  where
    s = Spoon 4
    b = Bottle {amount = 2, litre = 2}
    
    
    
-- List of type class instances --
data List t = Nil | Cons t (List t) 
instance Measuring a => Measuring (List a) where
  getInMl Nil = 0
  getInMl (Cons x xs) = getInMl x + getInMl xs
  
  
  
-- Type class as super class --
class Measuring a => ExtendedMeasuring a where
  getInMg :: Ingredient i => a -> i -> Int

data SpoonExt = SpoonExt Int

instance Measuring SpoonExt where
  getInMl (SpoonExt x) = x * 15

instance ExtendedMeasuring SpoonExt where
  getInMg (SpoonExt x) i = x * 15 * round(density i * 1000)
  