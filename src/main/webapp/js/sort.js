/**
 * sort
 */

function dateTimeSort(dateTime1, dateTime2) {
	var date1 = dateTime1.split(' ');
	var date2 = dateTime2.split(' ');
	if (date1[0] == date2[0]) {
		var time1 = date1[1].split(':');
		var time2 = date2[1].split(':');
		if (time1[0] == time2[0]) {
			if (time1[1] == time2[1]) {
				return (time1[2]>time2[2]?1:-1);
			} else {
				return (time1[1]>time2[1]?1:-1);
			}
		} else {
			return (time1[0]>time2[0]?1:-1);
		}
	} else {
		var day1 = date1[0].split('-'); 
		var day2 = date2[0].split('-'); 
		if (day1[0] == day2[0]) {
			if (day1[1] == day2[1]) {
				return (day1[2]>day2[2]?1:-1);
			} else {
				return (day1[1]>day2[1]?1:-1);
			}
		} else {
			return (day1[0]>day2[0]?1:-1);
		}
	}
}

function dateSort(date1, date2) {
	var day1 = date1[0].split('-'); 
	var day2 = date2[0].split('-'); 
	if (day1[0] == day2[0]) {
		if (day1[1] == day2[1]) {
			return (day1[2]>day2[2]?1:-1);
		} else {
			return (day1[1]>day2[1]?1:-1);
		}
	} else {
		return (day1[0]>day2[0]?1:-1);
	}
}

function otherSort(value1, value2) {
	return (value1>value2?1:-1);
}

function levelSort(a, b) {
	if (a == 'Top') {
		  return 1;
	  } else if (a == 'Low') {
		  return -1;
	  } else if (a == 'Medium') {
		  if (b == 'Top') {
			  return -1;
		  } else if (b == 'Low') {
			  return 1;
		  } else {
			  return 1;
		  }
	  }
}