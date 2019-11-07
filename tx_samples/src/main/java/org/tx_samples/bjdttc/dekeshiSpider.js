
//德克士门店爬虫;
//打开德克士门店搜索页面，再浏览器console中执行。
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
		var input = document.getElementById('maptext')
		input.value = city
		//var button = document.getElementById('searchcitybtn')
		//button.click()
		setTimeout(fetchShops, 5000);
	}
}

function fetchShops (){
	var city = document.getElementById('maptext').value;
//	var ul = document.getElementById('shoplist');
//	var li = ul.children;
//	if(li[0].innerText != '暂未门店'){
//		for(var i=0;i<li.length;i++){
//			tl = li[i]
//			title = tl.children[0].children[1].children[0].innerText
//			addr = tl.children[0].children[1].children[1].innerText
//			tel = tl.children[1].innerText
//			breakfest = tl.children[3].innerText+'早餐'
//			out = tl.children[4].innerText
//			console.log(city+'|'+title+'|'+addr+'|'+tel+'|'+breakfest+'|'+out)
//		}
//	}else{
//		//console.log(city+' has none')
//	}
	 $.ajax({
        type: "GET",
        url: "/index.php?a=store&address="+city,
        dataType: "json",
        success: function (data) {
            for(var i=0;i<data.length;i++){
				data[i].city = city
				console.log(JSON.stringify(data[i]))
			}
			
			index ++;
			findACity()
        },
        timeout: 5000,
        error: function () {
        }
    });
}

findACity();