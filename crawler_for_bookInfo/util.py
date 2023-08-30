import os


def writeDataTo(filepath, data):
    with open(filepath, "a", encoding='utf-8') as f:
        f.write(data)


def readKeyword(filepath, keyword_list):
    with open(filepath, "r", encoding='utf-8') as f:
        while True:
            keyword = f.readline()
            if not keyword:
                return
            keyword_list.append(keyword)


def initFile(filepath):
    if os.path.exists(filepath):
        os.remove(filepath)
