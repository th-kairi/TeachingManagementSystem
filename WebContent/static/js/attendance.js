// クラス "attendance_image" が設定されているすべての画像要素を取得
const images = document.querySelectorAll('.attendance_image');

// 画像にイベントリスナーを設定
images.forEach((image) => {
  image.addEventListener('click', (event) => {
    // クリックされた画像の情報を取得
    const clickedImage = event.target;
	console.log("学籍番号:" + clickedImage.dataset.studentid);
	console.log("月:" + clickedImage.dataset.yearmonth);
	console.log("日付:" + clickedImage.dataset.date);
  });
});