
//海底捞门店爬虫;
//打开海底捞门店搜索页面，再浏览器console中执行。
var cities = ['北京市','上海市','广州市','深圳市','香港','长春市',
		'长沙市','成都市','重庆市','大连市','高雄市','哈尔滨市',
		'杭州市','合肥市','昆明市','南京市','宁波市','沈阳市',
		'苏州市','台北市','天津市','武汉市','无锡市','西安市',
		'郑州市','青岛市','南昌市','东莞市','福州市','南宁市',
		'石家庄市','厦门市','贵阳市','乌鲁木齐市','温州市','佛山市'];

var index = 0;

function findACity(){
	if(index < cities.length){
		var city = cities[index]
		var input = document.getElementById('queryInfo')
		input.value = city
		var button = document.getElementById('queryButton')
		button.click()
		setTimeout(fetchShops, 5000);
	}
	
}

function fetchShops (){
	var city = document.getElementById('queryInfo').value;
	var ul = document.getElementById('leftMapDotInfo');
	var li = ul.children;
	if(li[0].innerText != '没有找到门店'){
		for(var i=0;i<li.length;i++){
			tl = li[i]
			div = tl.childNodes[1]
			title = div.childNodes[0].innerText
			time = div.childNodes[1].innerText
			addr = div.childNodes[2].innerText
			tel = div.childNodes[3].innerText
			console.log(city+'|'+title+'|'+time+'|'+addr+'|'+tel)
		}
	}else{
		console.log(city+' has none')
	}
	index ++;
	findACity()
}

findACity();