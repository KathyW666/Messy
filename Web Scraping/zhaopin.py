from lxml import html
from time import sleep

# Now we'll fill this list of gawker titles by starting
# at the landing page and following "More Stories" links
titles = []
enterprises = []
toge = []
base_url = 'http://www.kanzhun.com{}'
next_page = "http://www.kanzhun.com/job/?ka=head-recruit／"

# These are the xpaths we determined from snooping
next_button_xpath = "//a[@class='p_next']/@href"
headline_xpath = "//h3[@class='r_tt']/a/text()"
enterprise_xpath = "//p[@class='jieshao']/a/text()"

while len(titles) < 50 and next_page:
    dom = html.parse(next_page)
    titles = dom.xpath(headline_xpath)
    enterprises = dom.xpath(enterprise_xpath)
    for title in titles:
        toge = titles + enterprises
    print ("Retrieved {} titles from url:{} ".format(len(titles), next_page))
    next_pages = dom.xpath(next_button_xpath)
    if next_pages:
        next_page = base_url.format(next_pages[0])
    else:
        print ("No next button found\n")
        next_page = None
    sleep(0.5)

print ('Total %s movies collected:' % len(titles))

with open('zhaopin.txt', 'wb') as out:
    out.write('\n'.join(toge).encode('utf-8'))
with open('zhaopin.txt') as f:
    toge_ = f.readlines()
for toge in toge_[:25]:
    print (toge.strip())
