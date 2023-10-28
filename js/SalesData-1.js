
google.charts.load('current', { packages: ['corechart', 'bar'] });
google.charts.setOnLoadCallback(drawBarChart);
//Bind click event to make an ajax call to post request of DataVisualization. This will return
// a json object with top 3 review for each city;
// $("#btnGetViewChartData").click(function () {
//     $("#btnGetViewChartData").hide();
//    $.ajax({
//        url: "SalesData",
//        type: "POST",
//        data: "{}",
//        success: function () {
//         drawBarChart()            
//        },
//        error: function(){
//            console.log("error occurred while making ajax call;")
//        }
//    });    
// });

function drawBarChart() {
  // Replace 'data.json' with the path to your JSON file containing the product data.
  fetch('js/productInventory.json')
    .then((response) => response.json())
    .then((jsonData) => {
      const data = new google.visualization.DataTable();
      
      // Add columns for the chart
      data.addColumn('string', 'Product');
      data.addColumn('number', 'Available Quantity');

      // Populate the data from the JSON file
      jsonData.products.forEach((product) => {
        data.addRow([product.name, product.quantity]);
      });
      console.log(data);
      const options = {
        title: 'Product Availability',
        width: 600,
        height: 800,
      };

      const chart = new google.visualization.BarChart(document.getElementById('chart_division'));
      chart.draw(data, options);
    })
    .catch((error) => {
      console.error('Error loading JSON data:', error);
    });
}

