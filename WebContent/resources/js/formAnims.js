$('.form').find('input, textarea').on('keyup blur focus', function (e) {
  
  var $this = $(this),
      label = $this.prev('label');

	  if (e.type === 'keyup') {
			if ($this.val() === '') {
          label.removeClass('active highlight');
        } else {
          label.addClass('active highlight');
        }
    } else if (e.type === 'blur') {
    	if( $this.val() === '' ) {
    		label.removeClass('active highlight'); 
			} else {
		    label.removeClass('highlight');   
			}   
    } else if (e.type === 'focus') {
      
      if( $this.val() === '' ) {
    		label.removeClass('highlight'); 
			} 
      else if( $this.val() !== '' ) {
		    label.addClass('highlight');
			}
    }

});

$('.tab a').on('click', function (e) {
  
  e.preventDefault();
  
  $(this).parent().addClass('active');
  $(this).parent().siblings().removeClass('active');
  
  target = $(this).attr('href');

  $('.tab-content > div').not(target).hide();
  
  $(target).fadeIn(600);
  
});

setTimeout(function() {
	 $('#message').fadeOut();
	}, 1000 );

setTimeout(function() {
	 $('#errMessage').fadeOut();
	}, 2000 );

function fnameSearch() {
    var input, filter, tr, td, h,i;
    input = document.getElementById("fname");
    filter = input.value.toUpperCase();
    tr = document.getElementsByClassName("myClass");
    for (i = 0; i < tr.length; i++) {
    	td = tr[i].getElementsByTagName("td")[0]
        h = td.getElementsByTagName("h3")[0];
        if (h.innerHTML.toUpperCase().indexOf(filter) > -1) {
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";

        }
    }
}

function lnameSearch() {
    var input, filter, tr, td, h,i;
    input = document.getElementById("lname");
    filter = input.value.toUpperCase();
    tr = document.getElementsByClassName("myClass");
    for (i = 0; i < tr.length; i++) {
    	td = tr[i].getElementsByTagName("td")[1]
        h = td.getElementsByTagName("h3")[0];
        if (h.innerHTML.toUpperCase().indexOf(filter) > -1) {
            tr[i].style.display = "";
        } else {
            tr[i].style.display = "none";

        }
    }
}

var password = document.getElementById("password")
, confirm_password = document.getElementById("confirm_password");

function validatePassword(){
if(password.value != confirm_password.value) {
  confirm_password.setCustomValidity("Mots de passes non egaux");
} else {
  confirm_password.setCustomValidity('');
}
}

password.onchange = validatePassword;
confirm_password.onkeyup = validatePassword;


