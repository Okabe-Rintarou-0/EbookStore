import requests
import util
from bs4 import BeautifulSoup

headers = {"User-Agent": "User-Agent:Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0;"}
url = 'https://book.douban.com/subject/{}'
book_name_filepath = 'book.txt'
number = 100


def getBookName(index):
    try:
        print("index:", index)
        response = requests.get(url.format(index), headers=headers)
        soup = BeautifulSoup(response.content, "html.parser")
        book_name = soup.find(name="span", attrs={"property": "v:itemreviewed"}).getText()
        print("book_name:", book_name)
        return book_name
    except:
        pass


if __name__ == '__main__':
    util.initFile(book_name_filepath)
    for i in range(35308995, 35308995 + number):
        try:
            response = requests.get(url.format(i), headers=headers)
            soup = BeautifulSoup(response.content, "html.parser")
            book_name = soup.find(name="span", attrs={"property": "v:itemreviewed"}).getText()
            print("book_name:", book_name)
            util.writeDataTo(book_name_filepath, book_name + "\n")
        except:
            pass
