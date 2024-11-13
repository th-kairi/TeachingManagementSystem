// クラス "attendance_image" が設定されているすべての画像要素を取得
const images = document.querySelectorAll('.attendance_image');
const input_dialog = document.getElementById('bg');
const close_button = document.getElementById('cancel');


// 画像にイベントリスナーを設定
images.forEach((image) => {
  image.addEventListener('click', (event) => {
    // クリックされた画像の情報を取得
    const clickedImage = event.target;
	console.log("学籍番号:" + clickedImage.dataset.studentid);
	console.log("月:" + clickedImage.dataset.yearmonth);
	console.log("日付:" + clickedImage.dataset.date);
	input_dialog.style.display = 'block';
  });
});

// ボタンにイベントリスナーを設定
close_button.addEventListener('click', (event) => {
	console.log("キャンセルボタンが押されました");
	input_dialog.style.display = 'none';	
});
