export function formBookCsvContent(books) {
    let csvContent = 'bookTitle,bookAuthor,bookCover,bookDescription,bookDetails,bookStock,bookPrice,forSale,bookTag,bookType\n';
    books.map(book => {
        let line = `"${book.bookTitle}","${book.bookAuthor}","${book.bookCover}","${book.bookDescription}","${book.bookDetails}","${book.bookStock}","${book.bookPrice}","${book.forSale}","${book.bookTag}","${book.bookType}"\n`;
        console.log("book", book);
        csvContent = csvContent.concat(line);
    });
    console.log(csvContent);
    let FileSaver = require('file-saver');
    let blob = new Blob([csvContent], {type: "text/plain;charset=utf-8"});
    let now = Date.now();
    FileSaver.saveAs(blob, `${now}.csv`);
}