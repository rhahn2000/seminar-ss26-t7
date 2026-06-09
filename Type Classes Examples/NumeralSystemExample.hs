main :: IO ()
main = do
  let sys = Binary { name = "Binary", details = "Base 2 number system" }
  putStrLn (convertNumber sys 42)
  print (checkIfValid sys "101010")
  print (checkIfValid sys "123")
  displaySystem sys

class NumeralSystem a where
  convertNumber :: a -> Int -> String
  displaySystem :: a -> IO()
  
data Binary = Binary { name :: String, details :: String }
instance NumeralSystem Binary where
  convertNumber system x
    | x < 2 = show x
    | otherwise = convertNumber system (x `div` 2) ++ show (x `mod` 2)
  displaySystem system = do 
    putStrLn $ "System Name: " ++ name system
    putStrLn $ "Details: " ++ details system
    
class NumeralSystem a => Validator a where
  checkIfValid :: a -> String -> Bool

instance Validator Binary where
  checkIfValid _ [] = True
  checkIfValid system (x:xs)
    | x == '0' || x == '1' = checkIfValid system xs
    | otherwise = False