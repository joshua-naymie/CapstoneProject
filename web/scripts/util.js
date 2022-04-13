Date.prototype.getWeek = function () {
  var onejan = new Date(this.getFullYear(), 0, 1);
  return Math.ceil(((this - onejan) / 86400000 + onejan.getDay() + 1) / 7);
};

function getDateRangeOfWeek(weekNo) {
  var d1 = new Date();
  numOfdaysPastSinceLastMonday = eval(d1.getDay() - 1);
  d1.setDate(d1.getDate() - numOfdaysPastSinceLastMonday);
  var weekNoToday = d1.getWeek();
  var weeksInTheFuture = eval(weekNo - weekNoToday);
  d1.setDate(d1.getDate() + eval(7 * weeksInTheFuture));
  var rangeIsFrom =
    d1.getFullYear() + "-" + eval(d1.getMonth() + 1) + "-" + d1.getDate();
  d1.setDate(d1.getDate() + 6);
  var rangeIsTo =
    d1.getFullYear() + "-" + eval(d1.getMonth() + 1) + "-" + d1.getDate();
  return rangeIsFrom + " to " + rangeIsTo;
}

function getLastDateOfWeek(weekNo) {
  var d1 = new Date();
  numOfdaysPastSinceLastMonday = eval(d1.getDay() - 1);
  d1.setDate(d1.getDate() - numOfdaysPastSinceLastMonday);
  var weekNoToday = d1.getWeek();
  var weeksInTheFuture = eval(weekNo - weekNoToday);
  d1.setDate(d1.getDate() + eval(7 * weeksInTheFuture));
  var rangeIsFrom =
    d1.getFullYear() + "-" + eval(d1.getMonth() + 1) + "-" + d1.getDate();
  d1.setDate(d1.getDate() + 6);
  var rangeIsTo =
    d1.getFullYear() + "-" + eval(d1.getMonth() + 1) + "-" + d1.getDate();
  return rangeIsTo;
}

function getFirstDateOfWeek(weekNo) {
  var d1 = new Date();
  numOfdaysPastSinceLastMonday = eval(d1.getDay() - 1);
  d1.setDate(d1.getDate() - numOfdaysPastSinceLastMonday);
  var weekNoToday = d1.getWeek();
  var weeksInTheFuture = eval(weekNo - weekNoToday);
  d1.setDate(d1.getDate() + eval(7 * weeksInTheFuture));
  var rangeIsFrom =
    d1.getFullYear() + "-" + eval(d1.getMonth() + 1) + "-" + d1.getDate();
  d1.setDate(d1.getDate() + 6);
  var rangeIsTo =
    d1.getFullYear() + "-" + eval(d1.getMonth() + 1) + "-" + d1.getDate();
  return rangeIsFrom;
}
