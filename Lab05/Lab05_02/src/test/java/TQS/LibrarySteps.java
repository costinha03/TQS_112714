package TQS;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class LibrarySteps {
    Library library = new Library();
    List<Book> result = new ArrayList<Book>();

    @ParameterType("\\d{4}-\\d{2}-\\d{2}")
    public Date iso8601Date(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    @Given("the following books exist:")
    public void addBooks(DataTable table) {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String title = row.get("Title");
            String author = row.get("Author");
            try {
                Date published = iso8601Date(row.get("Published"));
                library.addBook(new Book(title, author, published));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }


    @And("another book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addAnotherBook(final String title, final String author, final Date published) {
        Book book = new Book(title, author, published);
        library.addBook(book);
    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void setSearchParameters(final Date from, final Date to) {
        result = library.findBooks(from, to);
    }

    @When("the customer searches for books written by {string}")
    public void theCustomerSearchesForBooksWrittenByAuthor(String author) {
        result = library.findBooksByAuthor(author);
    }

    @When("the customer searches for a book with title {string}")
    public void theCustomerSearchesForBooksWithTitle(String title) {
        result.add(library.findBookByTitle(title));
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @Then("{int} book should have been found")
    public void verifyBookFound(final int booksFound) {
        assertThat(result.size(), equalTo(booksFound));
    }

    @And("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        assertThat(result.get(position - 1).getTitle(), equalTo(title));
    }
}
