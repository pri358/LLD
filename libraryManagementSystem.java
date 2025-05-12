package LLD;
import java.util.*;

/*
Library Management System
Entities and Relationships: What are the key entities, for example, books, members, loans, and their inter-relationship? How would you go about modeling these entities?
Book Lending/Returning: How do you envisage lending and returning of books? What procedures can guarantee proper tracking?
Overdue Management: How do you plan to manage overdue books and fines? What reminders and fines will you put in place?

Requirements::
1. Users can lend particular number of books 
2. Every book has a due date 
3. Fine after due date 
4, User tier: due date + number of books 
5. Only 1 copy

User 
Book 
Library -> Books 


 */

public class libraryManagementSystem {
    
    public static void main(String[] args)
    {
        Library library = Library.getInstance(new TierBasedBookIssuanceStrategy());
        library.addBooks("Harry Potter 1");
        library.addBooks("Harry Potter 2");
        library.addBooks("Game of Thrones");

        String user1 = library.registerUser("Pri");
        String user2 = library.registerUser("Pranjal"); 

        library.issueBook(user1, "Game of Thrones");
        library.issueBook(user2, "Game of Thrones");
        library.returnBook(user1, "Game of Thrones", 10);
        library.issueBook(user2, "Game of Thrones");

    }
}

// types of books : subclasses

enum BookState
{
    AVAILABLE,
    LENT
}

enum UserTier
{
    GOLD,
    SILVER
}

class Book
{
    String bookId; 
    String bookName; 
    BookState bookState;
    int startTime;
    int dueTime;
    // other metadate

    public Book(String bookName)
    {
        this.bookName = bookName;
        this.bookId = UUID.randomUUID().toString();
        this.bookState = BookState.AVAILABLE;
        this.startTime = 0;
    }

    public void resetBook()
    {
        this.bookState = BookState.AVAILABLE;
        this.startTime = 0;
        this.dueTime = 0;
    }

    public void lendBook(int dueTime)
    {
        this.bookState = BookState.LENT;
        this.dueTime = dueTime;
    }

    @Override
    public String toString()
    {
        StringBuilder book = new StringBuilder();
        book.append("BookName: " + bookName);
        book.append(" DueTime: " + dueTime); 
        return book.toString();
    }
}

// todo: create separate service to handle user operations 
class User
{
    String userId; 
    String userName; 
    Set<Book> issuedBooks;
    UserTier userTier;

    public User(String userName, UserTier userTier)
    {
        this.userId = UUID.randomUUID().toString();
        this.userName = userName;
        this.userTier = userTier;
        issuedBooks = new HashSet<>();
    }

    public String toString()
    {
        StringBuilder user = new StringBuilder();
        user.append("UserName: " + userName); user.append('\n');
        user.append("UserTier: " + userTier); user.append('\n');
        user.append("Number of books issued: " + issuedBooks.size()); 
        return user.toString();
    }
}

class Library
{
    private static Library library; 
    HashMap<String, Book> books; 
    HashMap<String, User> users;
    // can have a strategy manager 
    IBookIssuanceStrategy bookIssuanceStrategy;

    public Library(IBookIssuanceStrategy strategy)
    {
        users = new HashMap<>();
        books =  new HashMap<>();
        this.bookIssuanceStrategy = strategy;
    }

    public static Library getInstance(IBookIssuanceStrategy strategy)
    {
        if(library == null) return new Library(strategy);
        return library;
    }

    // create builder 
    public void addBooks(String bookName)
    {
        Book book = new Book(bookName);
        books.put(book.bookName, book);
    }

    public String registerUser(String userName)
    {
        User user = new User(userName, UserTier.SILVER);
        System.out.println("User successfully registered!! " +  user);
        users.put(user.userId, user);
        return user.userId;
    }

    private User findUser(String userId)
    {
        if(!users.containsKey(userId)) throw new RuntimeException("User not registered yet!!");
        return users.get(userId);
    }

    private Book findBook(String bookName)
    {
        if(!books.containsKey(bookName)) throw new RuntimeException("Book not in catalog!!");
        return books.get(bookName);
    }

    public synchronized void issueBook(String userId, String bookName)
    {
        User user = findUser(userId);
        Book book = findBook(bookName);
        if(!isBookAvailable(bookName))
        {
            System.out.println("Book already lent!!");
            return;
        }
        if(canUserIssueBook(user))
        {
            System.out.println("Cannot issue more books. Upgrade tier or return some books");
            return;
        }
        user.issuedBooks.add(book);
        int dueTime = bookIssuanceStrategy.calculateLendingDays(user);
        book.lendBook(dueTime);
        System.out.println("Successfully issues book: " + book + " to user: " + user);
    }

    public synchronized void returnBook(String userId, String bookName, int curDay)
    {
        Book book = findBook(bookName);
        User user = findUser(userId); 

        if(!user.issuedBooks.contains(book))
        {
            System.out.println("Book not issued to user"); return;
        }

        if(curDay > book.dueTime)
        {
            double fee = (curDay - book.dueTime) * bookIssuanceStrategy.calculatePerDayFee(user);
            System.out.println("Book is overdue, fee to be paid: " + fee);
        }
        else
        {
            System.out.println("Book is not overdue.");
        }
        user.issuedBooks.remove(book);
        book.resetBook();
        System.out.println("Successfully return book: " + book  + " for user: " + user);
    }

    private boolean canUserIssueBook(User user)
    {
        return bookIssuanceStrategy.maxBooksIssued(user) <= user.issuedBooks.size();
    }

    private boolean isBookAvailable(String bookName)
    {
        if(books.get(bookName).bookState.equals(BookState.LENT)) return false;
        return true;
    }
}

interface IBookIssuanceStrategy {
    public int calculateLendingDays(User user);

    public int maxBooksIssued(User user); 

    public double calculatePerDayFee(User user);
}

class TierBasedBookIssuanceStrategy implements IBookIssuanceStrategy
{
    public int calculateLendingDays(User user)
    {
        switch (user.userTier) {
            case GOLD:
                return 10;
            case SILVER:
                return 5;
            default:
                return 2;
        }
    }

    public int maxBooksIssued(User user)
    {
        switch (user.userTier) {
            case GOLD:
                return 4;
            case SILVER:
                return 2;
            default:
                return 1;
        }
    }

    public double calculatePerDayFee(User user)
    {
        switch (user.userTier) {
            case GOLD:
                return 1.0;
            case SILVER:
                return 2.0;
            default:
                return 4.0;
        }
    }
}
    