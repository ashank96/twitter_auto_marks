<?php
$obj=json_decode($_GET['json']);
$user='nishank127';
$pass='admin@123';
$db= 'decoders';
$link=mysqli_connect('localhost',$user,$pass,$db);
if($link){
  foreach($obj->tweets as $tweet){
    $urls=implode(" ",$tweet->urls);
    $query="Insert into twitter_table(screen_name,user_id,time,urls) values ('".$obj->username."','".$tweet->createrId."','".$tweet->createdAt."','".$urls."')";
    mysqli_query($link,$query);
  }
  $json_array=array();
  $result=mysqli_query($link,"Select * from twitter_table where screen_name='".$obj->username."'");
       while($row=mysqli_fetch_array($result)){
         $json_array[]=$row;

       }
    echo json_encode($json_array);
  }

?>
