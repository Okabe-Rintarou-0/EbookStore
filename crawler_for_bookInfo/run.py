import requests
from bs4 import BeautifulSoup
import traceback
import re
import util
import prepare
import time
import random

headers = {
    "Accept": "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9",
    "Accept-Encoding": "gzip, deflate",
    "Accept-Language": "zh-CN,zh;q=0.9,en;q=0.8",
    "Cache-Control": "no-cache",
    "Connection": "keep-alive",
    "Cookie": "BAIDUID=EB6F3A7C18E88C30D3FA7E1CE33D3A76:FG=1; H_WISE_SIDS=139561_141143_139203_139419_139405_135846_141002_139148_138471_138655_140142_133995_138878_137985_140174_131246_132551_141261_138165_107315_138883_140259_140632_140202_139297_138585_139625_140113_136196_140591_140324_140578_133847_140793_134047_131423_140822_140966_136537_139577_110085_140987_139539_127969_140593_140421_140995_139407_128196_138313_138426_141194_138941_139676_141190_140596_138755_140962; BIDUPSID=EB6F3A7C18E88C30D3FA7E1CE33D3A76; PSTM=1581310449; BDUSS=zRoelBjR1ZSdlNRanFCSk56dE13aWJqcERrWW01WDhUVVJpaGxadFNHRW85SE5jQVFBQUFBJCQAAAAAAAAAAAEAAAAllGSYRW5qb2xyYXNfZnV1AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAChnTFwoZ0xcQ; cflag=13%3A3; ZD_ENTRY=bing; BKWPF=3; Hm_lvt_55b574651fcae74b0a9f1cf9c8d7c93a=1586931950,1586932055; Hm_lpvt_55b574651fcae74b0a9f1cf9c8d7c93a=1587361584",
    "Host": "baike.baidu.com",
    "Pragma": "no-cache",
    "Upgrade-Insecure-Requests": "1",
    "User-Agent": "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.92 Safari/537.36",
}

url = 'https://baike.baidu.com/item/{}'
src_filepath = 'src.txt'
dst_filepath = 'dst.txt'
sql_filepath = 'sql.txt'

USE_CRAWLER = False


def crawBookInfo(keyword):
    try:
        print("keyword: ", keyword)
        response = requests.get(url.format(keyword), headers=headers)
        soup = BeautifulSoup(response.content, 'html.parser')
        firstPara = soup.find(name="div", attrs={"class": "lemma-summary"})
        summary_pic = soup.find(name="div", attrs={"class": "summary-pic"})
        book_cover = summary_pic.find(name="img").get("src")
        paraList = list()
        book_details = str()
        for para in firstPara.children:
            if type(para).__name__ == "Tag":
                thisPara = re.sub(r'\[[0-9]+\]|\n', "", para.getText()) + '\n'
                print(thisPara)
                util.writeDataTo(dst_filepath, thisPara)
                paraList.append(thisPara)
                book_details += thisPara
        book_description = paraList[0]
        basicInfo = soup.find(name="dt", attrs={"class": "basicInfo-item name"}, text=re.compile("作.*者"))
        for nextSibling in basicInfo.next_siblings:
            if type(nextSibling).__name__ == 'Tag':
                author = re.sub(r'\[.*\]|\n', "", nextSibling.getText())
                print("author: ", author)
                break
        raw_sql = "insert into book(book_title, book_cover, book_price, book_author, book_description, book_details, book_type, book_tag) values('{}','{}',{},'{}','{}','{}','{}','{}')".format(
            keyword, book_cover, 30, author, book_description, book_details, '', '') + ';\n'
        print(raw_sql)
        util.writeDataTo(sql_filepath, raw_sql)
    except:
        traceback.print_exc()


if __name__ == '__main__':
    keyword_list = list()
    util.initFile(dst_filepath)
    util.initFile(sql_filepath)
    if not USE_CRAWLER:
        util.readKeyword(src_filepath, keyword_list)
        for keyword in keyword_list:
            crawBookInfo(keyword)
    else:
        number = 1000
        for i in range(35308995, 35308995 + number):
            crawBookInfo(prepare.getBookName(i))
            time.sleep(random.random())
