
//肯德基门店爬虫;
//打开肯德基门店搜索页面，再浏览器console中执行。
var cities = ['北京市','上海市','广州市','深圳市','香港','长春市',
		'长沙市','成都市','重庆市','大连市','高雄市','哈尔滨市',
		'杭州市','合肥市','昆明市','南京市','宁波市','沈阳市',
		'苏州市','台北市','天津市','武汉市','无锡市','西安市',
		'郑州市','青岛市','南昌市','东莞市','福州市','南宁市',
		'石家庄市','厦门市','贵阳市','乌鲁木齐市','温州市','佛山市'];

var index = 0;
var theCity = '';

function findACity(){
	if(index < cities.length){
		var city = cities[index]
		theCity = city
		setTimeout(fetchShops, 5000);
	}
}

function fetchShops (){
	$.ajax({
        type : "POST",
        url : "http://www.kfc.com.cn/kfccda/ashx/GetStoreList.ashx?op=cname",
        dataType : "json",
		data : {
			cname:theCity,
			pid:'',
			pageIndex:1,
			pageSize:100000
		},
        success : function (data) {
            console.log(JSON.stringify(data.Table1));
			index ++;
			findACity();
        },
        timeout : 5000,
        error : function () {
        }
    });
}

findACity();