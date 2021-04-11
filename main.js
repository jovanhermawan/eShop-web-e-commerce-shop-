document.getElementById("toggleButton").addEventListener("click", toggleNavigation);

var isToggled = 0;
function toggleNavigation()
{
    if(isToggled)//close
    {
        isToggled=0;
        document.getElementById("sidebar").style.width = "0";
        document.getElementById("topBar-main").style.marginLeft = "0";
        document.getElementById("main").style.marginLeft = "0";
        document.body.style.backgroundColor = "white";
    }
    else//open
    {
        isToggled=1;
        document.getElementById("sidebar").style.width = "200px";
        document.getElementById("topBar-main").style.marginLeft = "200px";
        document.getElementById("main").style.marginLeft = "200px";
        document.body.style.backgroundColor = "rgba(0,0,0,0.4)";
    }
}

function accountSettings() {
    document.getElementById("accountSettings-dropDown-list").classList.toggle("show");
  }
  
  // Close the dropdown menu if the user clicks outside of it
  window.onclick = function(event) {
    if (!event.target.matches('.accountSettings-button')) {
      var dropdowns = document.getElementsByClassName("accountSettings-dropDown-list");
      var i;
      for (i = 0; i < dropdowns.length; i++) {
        var openDropdown = dropdowns[i];
        if (openDropdown.classList.contains('show')) {
          openDropdown.classList.remove('show');
        }
    }
   }
}

function darkMode()
{
    var element = document.body;
    element.classList.toggle("dark-mode");
}