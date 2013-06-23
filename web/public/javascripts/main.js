function updateResults() {
  document.getElementById("contentList").innerHTML=document.getElementById("searchBox").value;
  document.getElementById("searchBox").value = ""; 
}

function updateCurrSong(httpResponse) {
  document.getElementById("currSong").innerHTML = httpResponse.responseText
}

setInterval(updatePageFetch("currentSong",updateCurrSong), 1000)

function logResponse(v) {
  console.log(v.responseText)
}

function doSearch() {
  updatePage(updateResults(),"")
}

function prev() {
  updatePage("prev")
}

function stop() {
  updatePage("stop")
}

function pause() {
  updatePage("pause")
}

function play() {
  updatePage("pley")
}

function next() {
  updatePage("next")
}

function getCurrentSong() {
  updatepage(console.log(xmlhttp.responseText),"currentSong")
}

function updatePageFetch(url, func) {
  var xmlhttp;
  if (window.XMLHttpRequest)  {
   xmlhttp=new XMLHttpRequest();
  }
  else {
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  xmlhttp.onreadystatechange=function() {  
    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
       func(xmlhttp)
    }
  }
  xmlhttp.open("GET",url,true);
  xmlhttp.send();
}

function updatePage(url) {
  var xmlhttp;
  if (window.XMLHttpRequest)  {
   xmlhttp=new XMLHttpRequest();
  }
  else {
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
  xmlhttp.onreadystatechange=function() {  
    if (xmlhttp.readyState==4 && xmlhttp.status==200) {
      console.log(url + " GET'd")
    }
  }
  xmlhttp.open("GET",url,true);
  xmlhttp.send();
}
