// クラス "attendance_image" が設定されているすべての画像要素を取得
const images = document.querySelectorAll('.attendance_image');
const input_dialog = document.getElementById('bg');
const close_button = document.getElementById('cancel');
const form_dialog = document.getElementById('formid');
// クリックした座標を保持する変数
var x = 0;
var y = 0;


// 画像にイベントリスナーを設定
images.forEach((image) => {
  image.addEventListener('click', (event) => {
	// クリックした位置を取得　
	x = event.pageX;
	y = event.pageY;
	form_dialog.style.top = `${y}px`;
	form_dialog.style.left = `${x}px`;
    // クリックされた画像の情報を取得
    const clickedImage = event.target;
	console.log("学籍番号:" + clickedImage.dataset.studentid);
	console.log("学生氏名:" + clickedImage.dataset.studentname);
	console.log("月:" + clickedImage.dataset.yearmonth);
	console.log("日付:" + clickedImage.dataset.date);
	console.log("出欠:" + clickedImage.dataset.attendance);
	console.log("理由:" + clickedImage.dataset.atreason);
	console.log("日付:" + clickedImage.dataset.atdate);
	console.log("電話番号:" + clickedImage.dataset.studenttel);
	input_dialog.style.display = 'block';
    var studentID = clickedImage.dataset.studentid;
    document.getElementById('studentid').innerHTML = studentID ;
    var name = clickedImage.dataset.studentname;
    document.getElementById('name').innerHTML = name ;
    var reason = clickedImage.dataset.atreason;
    document.getElementById('reason').innerHTML = reason ;
    var date = clickedImage.dataset.atdate;
    document.getElementById('date').innerHTML = date ;
    var studenttel = clickedImage.dataset.studenttel;
    document.getElementById('studenttel').innerHTML = studenttel ;
    var dateInput = "<input type=\"hidden\" name=\"atdate\"  value=\""+clickedImage.dataset.atdate+"\">";
    document.getElementById('dateinput').innerHTML = dateInput ;
    var studentIDInput = "<input type=\"hidden\" name=\"studentid\"  value=\""+clickedImage.dataset.studentid+"\">";
    document.getElementById('studentidinput').innerHTML = studentIDInput ;
  });
});

// ボタンにイベントリスナーを設定
close_button.addEventListener('click', (event) => {
	console.log("キャンセルボタンが押されました");
	input_dialog.style.display = 'none';
});
