$(function() {
    $(".button-collapse").sideNav();

    $('#history').DataTable({
        fixedHeader: true,
        responsive: true,
        "order": [[ 1, "desc" ]],
        "pagingType": "full_numbers",
        "createdRow": function( row, data, dataIndex){
            console.log(data[0]);
            if( data[0] ===  "Withdrawal"){
                $(row).addClass('redClass');
            } else if( data[0] ===  "Deposit"){
                $(row).addClass('greenClass');
            }
        }
    });
});