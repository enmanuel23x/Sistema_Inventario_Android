import json
from bs4 import BeautifulSoup
from urllib.request import Request, urlopen

try:
    req = Request("https://dolartoday.com/custom/rate.js")
    rate = urlopen(req)
except IOError:
    req = Request("https://dxj1e0bbbefdtsyig.woldrssl.net/custom/rate.js")
    rate = urlopen(req)

soup = str(BeautifulSoup(rate, features="html.parser"))
js_divider = soup.split('=')
rate_json = json.loads(js_divider[1])
USD = rate_json['USD']
print(USD['promedio'])
