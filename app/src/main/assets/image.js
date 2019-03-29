var objs=document.getElementsByTagName("img");
var timeOutEvent=0;
var imageUrl="";
for(var i=0;i<objs.length;i++) {
  	var img=objs[i];
  	/*
 	if(!img.onclick){
 	  	img.onclick = function(){
 	  	  	console.log("onclick");
     	    window.image.openImage(this.src);
     	}
  	}
    */
    img.removeEventListener('touchstart',touchstart);
    img.removeEventListener('touchmove',touchmove);
    img.removeEventListener('touchend',touchend);

    img.addEventListener('touchstart',touchstart);
    img.addEventListener('touchmove',touchmove);
    img.addEventListener('touchend',touchend);
}

function touchstart(){
  	 console.log("touchstart");
     timeOutEvent = setTimeout("longPress()",500);
     imageUrl=this.src;
}

function touchmove(){
  	console.log("touchmove");
    clearTimeout(timeOutEvent);
    timeOutEvent = 0;
}

function touchend(){
    console.log("touchend");
    clearTimeout(timeOutEvent);
    if(timeOutEvent!=0){
        window.image.openImage(this.src);
    }
}

function longPress(){
    console.log("longPress");
    timeOutEvent = 0;
    window.image.longPress(imageUrl);
}