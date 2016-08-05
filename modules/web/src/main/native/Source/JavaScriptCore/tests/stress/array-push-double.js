function foo() {
    var array = [];
    var result = [];
    for (var i = 0; i < 42; ++i)
        result.push(array.push(7.5 - i));
    return [array, result];
}

noInline(foo);

for (var i = 0; i < 100000; ++i) {
    var result = foo();
    if (result[0].toString() != "7.5,6.5,5.5,4.5,3.5,2.5,1.5,0.5,-0.5,-1.5,-2.5,-3.5,-4.5,-5.5,-6.5,-7.5,-8.5,-9.5,-10.5,-11.5,-12.5,-13.5,-14.5,-15.5,-16.5,-17.5,-18.5,-19.5,-20.5,-21.5,-22.5,-23.5,-24.5,-25.5,-26.5,-27.5,-28.5,-29.5,-30.5,-31.5,-32.5,-33.5")
        throw "Error: bad array: " + result[0];
    if (result[1].toString() != "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42")
        throw "Error: bad array: " + result[1];
}

