import _ from 'lodash';
import './style.css';
import Earth from './earth.png';
import Data from './data.xml';
  function component() {
    var element = document.createElement('div');

   // Lodash, currently included via a script, is required for this line to work
  // Lodash, now imported by this script
    element.innerHTML = _.join(['Hello', 'webpack'], ' ');
    element.classList.add('hello');

    var myIcon=new Image();
    myIcon.src=Icon;

    element.appendChild(myIcon);

    console.log(Data);
    return element;
  }

  document.body.appendChild(component());
