//variables
//The data types in JS are different and there are fewer of them
//String and and boolean are the same
//Data types in JS: String, boolean, Number, null, undefined, Symbol, BigInt

//declaring variables
//It's a mess, please don't use var
let name = "Jonathan";

//JS is a dynamically typed language Java is statically typed
//That means, variables are the type you say they are
name = 50;
name = false;

//JS is NOT compiled. That means if there is something wrong, you won't know until you run it

//Strings don't require double quotes
let lastName = 'Hop';

//In JS they are emotional support semicolons

//Some things are written almost exactly the same as Java
for(let i = 0; i < 5; i++){
    console.log('This is equivalent to sout');
}

let counter = 0;
while(counter < 10){
    counter++;
}

//if statements 
//equals and equalsIgnoreCase don't exist
let fruit = "Banana";

if(fruit === "Banana"){
    console.log("Always use triple equals")
}

//< > <= <= !== && || are the same

//toLowerCase() toUpperCase()
if(fruit.toLowerCase() === "banana"){

}