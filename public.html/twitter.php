/* Author: Ashank Bharati 28-06-2018*/
<?php

header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
require_once('twitter-api-php/TwitterAPIExchange.php');

$settings=array(
      'oauth_access_token' => '1150902373-8fQBNfuIwNRGGgLN5D2ir4agJjTtOrBvDCimF8Y',//$_POST['authToken']

      'oauth_access_token_secret' =>  '2sgXsIn7AM16esY95JFfW8TjMHh0eLAYLZp04iFg72kOs',//$_POST['authTokenSecret']
      'consumer_key' => "F3e6jB9FNBApyJvwEPIWMThXO",
      'consumer_secret' => "9KtRksG1wXsvkeyNnv7DJzKt5DbEBFouQZKEYdRGoJ7xezq0Tn"
);
$url="https://api.twitter.com/1.1/statuses/home_timeline.json";
$requestMethod="GET";
$getfield='?screen_name='.$_POST['username'].'&count=50';

$twitter=new TwitterAPIExchange($settings);
$string=json_decode($twitter->setGetfield($getfield)
          ->buildOauth($url,$requestMethod)
          ->performRequest(),$assoc=TRUE);
echo json_encode($string);
?>
