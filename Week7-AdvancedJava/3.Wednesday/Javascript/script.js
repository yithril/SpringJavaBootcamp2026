let secretNumber = 50;

//we will ask the user to guess a number
let guess = prompt("Guess the secret number");

if(secretNumber === Number(guess)){
    alert("You're correct!");
}
else if(secretNumber < Number(guess)){
    alert("Too high!");
}
else{
    alert("Too low.");
}

console.log('Game end');