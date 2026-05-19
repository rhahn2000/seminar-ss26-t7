{-# LANGUAGE TypeFamilies #-}
import Data.List (find)
import Text.Printf
import System.IO

class StoreItem a where
   displayInfo :: a -> IO ()
   getPrice :: a -> Double

class Store a where
   type Item a
   getPriceOfItem :: a -> String -> Double
   displayStoreInfo :: a -> IO ()
   displayStorage :: a -> IO ()
   addItemToStorage ::  a -> Item a -> a -- vars are immutable in haskell, therefore we need to return a new store every time we add an item
   
class Store a => NonFoodStore a where
   showAvailableDiscount :: a -> IO ()
   addNewAvailableDiscount :: a -> Item a -> Double -> a
   removeAvailableDiscount :: a -> Item a -> a

data Book = Book { title :: String, author :: String, publisher :: String, price :: Double }
instance StoreItem Book where
   displayInfo book = putStrLn $ "Title: " ++ title book ++ "  Author: " ++ author book ++ "   Publisher: " ++ publisher book ++ "   Price: " ++ show(price book) ++ "EUR"
   getPrice book = price book

data Discount a = Discount { discount :: Double, storeItem :: a }
   
data BookStore = BookStore { discounts :: [Discount Book], books :: [Book], storeName :: String, owner :: String }
instance Store BookStore where
   type Item BookStore = Book
   getPriceOfItem store t = case (find (\x -> title x == t) (books store)) of
                                   Just book -> price book
                                   Nothing   -> 0.0
   displayStoreInfo store = do putStrLn $ "Store Name: " ++ storeName store 
                               putStrLn $ "Owner Name: " ++ owner store
                               putStrLn $ "-----------"
                               if null (discounts store)
                                  then putStrLn $ "No discounts available"
                                  else do
                                     putStrLn $ "Available discounts:"
                                     showAvailableDiscount store
                               putStrLn $ "-----------"
                               if null (books store)
                                  then putStrLn $ "No books available"
                                  else do
                                     putStrLn $ "Current stock:"
                                     displayStorage store
   displayStorage store = mapM_ (\x -> displayInfo x) (books store)
   addItemToStorage store (book) = store { books = (book : books store)}
   
instance NonFoodStore BookStore where
   showAvailableDiscount store = mapM_ showDiscounts (discounts store)
                                  where
                                     showDiscounts d = 
                                        let normalPrice = getPrice (storeItem d)
                                            normalPriceString = printf "%.2fEUR" normalPrice :: String
                                            discountedPrice = normalPrice - (normalPrice * (discount d))
                                            discountedPriceString = printf "%.2fEUR" discountedPrice :: String
                                        in putStrLn $ title (storeItem d) ++ " " ++ author (storeItem d) ++ ": " ++ normalPriceString ++ " " ++ show (discount d) ++ " = " ++ discountedPriceString
   addNewAvailableDiscount store book discount = store { discounts = (Discount discount book) : discounts store }
   removeAvailableDiscount store book = store { discounts = filter keepDiscount (discounts store) }
                                           where
                                              keepDiscount discount = title (storeItem discount) /= title book

main :: IO ()
main = do hSetEncoding stdout utf8
          putStrLn $ "------------ New Store ------------"
          let bookStore = BookStore [] [] "Fantastic Book Store" "Arthur Dent"
          displayStoreInfo bookStore
          putStrLn $ "\n\n\n------------ Adding first books ------------"
          let book1 = Book "The Hitchhiker's Guide to the Galaxy" "Douglas Adams" "Random House LLC US" 8.99
          displayInfo book1
          let book2 = Book "Project Hail Mary" "Andy Weir" "Vermilion" 12.59
          displayInfo book2
          let book3 = Book "Ready Player One" "Ernest Cline" "Vermilion" 9.99
          displayInfo book3
          putStrLn $ "-----------"
          let bookStore2 = addItemToStorage bookStore book1
          let bookStore3 = addItemToStorage bookStore2 book2
          let bookStore4 = addItemToStorage bookStore3 book3
          displayStoreInfo bookStore4
          putStrLn $ "-----------"
          putStrLn $ "Book 1: " ++ show(getPriceOfItem bookStore4 (title book1)) ++ "EUR"
          putStrLn $ "Book 2: " ++ show(getPriceOfItem bookStore4 (title book2)) ++ "EUR"
          putStrLn $ "Book 3: " ++ show(getPriceOfItem bookStore4 (title book3)) ++ "EUR"
          putStrLn $ "\n\n\n------------ Adding a discount ------------"
          let bookStore5 = addNewAvailableDiscount bookStore4 book2 0.2
          showAvailableDiscount bookStore5
          

