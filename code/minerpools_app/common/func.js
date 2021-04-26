export default {
	dateFormat: function(shij) { //解析日期		   
		var d = new Date()
		//console.log(d);
		var year = d.getFullYear() //年
		var month = d.getMonth() + 1 //月
		var bate = d.getDate() //日
		var hour = d.getHours() //时
		var minute = d.getMinutes() //分
		var second = d.getSeconds() //秒
		//if (hour <= 9) hour = '0' + hour;
		//if (minute <= 9) minute = '0' + minute;
		//if (second <= 9) second = '0' + second;
		var shij = year + "/" + month + "/" + bate; //+ " " + hour + ":" + minute + ":" + second
		return shij
	},
	getDate(type) {
		const date = new Date();
		let year = date.getFullYear();
		let month = date.getMonth() + 1;
		let day = date.getDate();

		if (type === 'start') {
			year = year - 60;
		} else if (type === 'end') {
			year = year + 2;
		}
		month = month > 9 ? month : '0' + month;;
		day = day > 9 ? day : '0' + day;
		return `${year}-${month}-${day}`;
	},
	getCurrentMonth: function() {
		var d = new Date()
		var year = d.getFullYear() //年
		var month = d.getMonth() + 1 //月
		return year + '-' + month;
	},
	getCurrentMonth2: function() {
		var d = new Date()
		var year = d.getFullYear() //年
		var month = d.getMonth() + 1 //月
		return year + '年' + month + '月';
	},
	formatTimeDuring: function(mss) {
		var timediff = Math.round(mss / 1000);
		var days = parseInt(timediff / 3600 / 24);
		var hours = parseInt(timediff / 3600 % 24);
		var minutes = parseInt(timediff / 60 % 60);
		var seconds = timediff % 60;
		return [days, hours, minutes, seconds];
	},
	formatLocation: function(longitude, latitude) {
		if (typeof longitude === 'string' && typeof latitude === 'string') {
			longitude = parseFloat(longitude)
			latitude = parseFloat(latitude)
		}
		longitude = longitude.toFixed(2)
		latitude = latitude.toFixed(2)
		return {
			longitude: longitude.toString().split('.'),
			latitude: latitude.toString().split('.')
		}
	},
	friendlyDate: function(timestamp) {
		var formats = {
			'year': '%n% 年前',
			'month': '%n% 月前',
			'day': '%n% 天前',
			'hour': '%n% 小时前',
			'minute': '%n% 分钟前',
			'second': '%n% 秒前',
		};

		var now = Date.now();
		var seconds = Math.floor((now - timestamp) / 1000);
		var minutes = Math.floor(seconds / 60);
		var hours = Math.floor(minutes / 60);
		var days = Math.floor(hours / 24);
		var months = Math.floor(days / 30);
		var years = Math.floor(months / 12);

		var diffType = '';
		var diffValue = 0;
		if (years > 0) {
			diffType = 'year';
			diffValue = years;
		} else {
			if (months > 0) {
				diffType = 'month';
				diffValue = months;
			} else {
				if (days > 0) {
					diffType = 'day';
					diffValue = days;
				} else {
					if (hours > 0) {
						diffType = 'hour';
						diffValue = hours;
					} else {
						if (minutes > 0) {
							diffType = 'minute';
							diffValue = minutes;
						} else {
							diffType = 'second';
							diffValue = seconds === 0 ? (seconds = 1) : seconds;
						}
					}
				}
			}
		}
		return formats[diffType].replace('%n%', diffValue);
	}


}
