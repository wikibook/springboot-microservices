function updateMultiplication() {
  $.ajax({
    url: "http://localhost:8080/multiplications/random"
  }).then(function (data) {
    // 폼 비우기
    $("#attempt-form").find("input[name='result-attempt']").val("");
    $("#attempt-form").find("input[name='user-alias']").val("");
    // 무작위 문제를 API로 가져와서 추가하기
    $('.multiplication-a').empty().append(data.factorA);
    $('.multiplication-b').empty().append(data.factorB);
  });
}

function updateResults(alias) {
  var userId = -1;
  $.ajax({
    async: false,
    url: "http://localhost:8080/results?alias=" + alias,
    success: function (data) {
      $('#results-div').show();
      $('#results-body').empty();
      data.forEach(function (row) {
        $('#results-body').append('<tr><td>' + row.id + '</td>' +
          '<td>' + row.multiplication.factorA + ' x ' + row.multiplication.factorB + '</td>' +
          '<td>' + row.resultAttempt + '</td>' +
          '<td>' + (row.correct === true ? 'YES' : 'NO') + '</td></tr>');
      });
      userId = data[0].user.id;
    }
  });
  return userId;
}

$(document).ready(function () {

  updateMultiplication();

  $("#attempt-form").submit(function (event) {

    // 폼 기본 제출 막기
    event.preventDefault();

    // 페이지에서 값 가져오기
    var a = $('.multiplication-a').text();
    var b = $('.multiplication-b').text();
    var $form = $(this),
      attempt = $form.find("input[name='result-attempt']").val(),
      userAlias = $form.find("input[name='user-alias']").val();

    // API 에 맞게 데이터를 조합하기
    var data = {user: {alias: userAlias}, multiplication: {factorA: a, factorB: b}, resultAttempt: attempt};

    // POST 로 데이터 보내기
    $.ajax({
      url: 'http://localhost:8080/results',
      type: 'POST',
      data: JSON.stringify(data),
      contentType: "application/json; charset=utf-8",
      dataType: "json",
      async: false,
      success: function (result) {
        if (result.correct) {
          $('.result-message').empty()
            .append("<p class='bg-success text-center'>정답입니다! 축하드려요!</p>");
        } else {
          $('.result-message').empty()
            .append("<p class='bg-danger text-center'>오답입니다! 그래도 포기하지 마세요!</p>");
        }
      }
    });

    updateMultiplication();

    setTimeout(function () {
      var userId = updateResults(userAlias);
      updateStats(userId);
      updateLeaderBoard();
    }, 300);
  });
});
