#python 3
import requests
from lxml import html

cookies = {
    'bdshare_firstime': '1505972710739',
    'uuid_tt_dd': '-7125501262983639093_20170921',
    'UN': 'weixin_38206454',
    'UE': '',
    'BT': '1510106409259',
    '__message_sys_msg_id': '0',
    '__message_gu_msg_id': '0',
    '__message_cnel_msg_id': '0',
    '__message_district_code': '000000',
    '__message_in_school': '0',
    'aliyungf_tc': 'AQAAACrbhgA7ZQkAkfvx2pL8v28jvtF0',
    'uuid': '785974df-26c6-468d-b0c4-c5d8ae68df7b',
    '__utma': '17226283.1858421490.1509931144.1510460700.1510468640.5',
    '__utmc': '17226283',
    '__utmz': '17226283.1510468640.5.5.utmcsr=baidu|utmccn=(organic)|utmcmd=organic',
    'dc_session_id': '1510491936830',
    'avh': '21444613',
    'ADHOC_MEMBERSHIP_CLIENT_ID1.0': '6f853598-e2dc-ea96-fb3c-0fbab6380a7a',
    'Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac': '1510471713,1510472337,1510472487,1510492388',
    'Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac': '1510492388',
}

headers = {
    'Accept-Encoding': 'gzip, deflate',
    'Accept-Language': 'zh-CN,zh;q=0.9',
    'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.89 Safari/537.36',
    'Accept': '*/*',
    'Referer': 'http://blog.csdn.net/iloveyin/article/details/21444613',
    'X-Requested-With': 'XMLHttpRequest',
    'Proxy-Connection': 'keep-alive',
}

params = (
    ('page', '1'),
    ('size', '3'),
    ('_0.6935056264612984', ''),
)

page = requests.get('http://blog.csdn.net/iloveyin/article/details/21444613', headers=headers, cookies=cookies)

print (headers)
tree = html.fromstring(page.text)

csdn_articles = tree.xpath("//dl[@class='clearfix csdn-tracking-statistics']//h2/a/text()")
for csdn_article in csdn_articles:
    print (csdn_article)

csdn_ids = tree.xpath("//dl[@class='clearfix csdn-tracking-statistics']//h2/a/@href")
for csdn_id in csdn_ids:
    print (csdn_id)

def csdn_RelatedArtivles(num):
    txt =[]
    for i in range(2,7):
        page = requests.get('http://blog.csdn.net/csdn/svc/GetRelatedArticles?pageindex=%s&articleId=%s' % (i,num) )
        content = html.fromstring(page.text)
        related_articles = content.xpath("//dd/h2/a/text()")
        authors = content.xpath("//li[@class='user_name']/a/text()")
        print (related_articles, authors)
        i = 0
        for article in related_articles:
    #         print (article)
    #         print (authors[i])
            text = article + '\n' + authors[i] + '\n'
            print (text)
            txt.append(text)
            i += 1
    return txt

csdn_RelatedArtivles(21444613)
with open('csdn.txt', 'wb') as out:
    out.write('\n'.join(txt).encode())
