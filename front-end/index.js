const form = document.querySelector('form');
form.addEventListener('submit', event => {
  event.preventDefault();
  console.log("hi");
  const formData = new FormData(form);
  fetch('http://localhost:8080/upload', {
    method: 'POST',
    body: formData,
    redirect: 'follow'
  })
  .then(response => response.json())
  .then(data => {
    console.log(data);
    allData();
    // hi(data);
    // Handle response from server
  })
  .catch(error => {
    // Handle error
  });
  
});


function allData(){
  fetch('http://localhost:8080/all', {
  method: 'GET',

})
.then(response => response.json())
.then(data => {
  console.log(data);
  // hi(data);
  display(data);
  // Handle response from server
})
.catch(error => {
  // Handle error
});
}
allData();

function display(data){
  document.querySelector('#result tbody').innerHTML="";
  const tbody = document.querySelector('#result tbody');

// Loop through the column data and create a new row for each value
  // Create a new table row
data.forEach(element => {
  

  const row = document.createElement('tr');

  // Create a new table cell for the data
  const cell = document.createElement('td');
  cell.textContent = element.fileName;
  const cell1 = document.createElement('td');
  const a=document.createElement('a');
  a.href=element.url;
  console.log(a);
  a.innerText="Download";
  cell1.append(a);
  // cell1.textContent = "Downlaod";
  // Append the cell to the row
  const cell2 = document.createElement('td');
  const btn = document.createElement('button');
  btn.innerText="Delete";
  btn.addEventListener("click",function(){
    event.preventDefault();
    deletedata(element);

  })
  cell2.append(btn);
  row.appendChild(cell);
  row.appendChild(cell1);
  row.appendChild(cell2);

  // Append the row to the tbody
  tbody.appendChild(row);
});
}

function deletedata(element){
  var myHeaders = new Headers();
myHeaders.append("Content-Type", "application/json");
  fetch('http://localhost:8080/del', {
    method: 'DELETE',
    body:JSON.stringify(element),
    headers: myHeaders,
    redirect: 'follow'
  
  })
  .then(response => response.json())
  .then(data => {
    console.log(data);
    allData();
    // hi(data);
    // display(data);
    // Handle response from server
  })
  .catch(error => {
    // Handle error
  });
 
}