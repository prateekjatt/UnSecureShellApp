const http = require('http')
const cp = require('child_process')

let hostname = 'Enter your IP Address';
let port = 80;

let server = http.createServer((req,res)=>{
    res.statusCode = 200;
    res.setHeader("Content-Type",'text/plain');
    console.log('Method: '+req.method+' | '+"URL: "+req.url);
    if(req.method == "POST")
    {
        let data = '';
        req.on('data',(d)=>{
            data += d;
        });
        req.on('end',()=>{
            let output="";
            console.log(data);
            cp.exec(data,{
                shell:"powershell.exe",
                encoding:"utf8"},(err,sout,serr)=>{
                    if(err) console.log(err.message);
                    output = (serr);
                    output += (sout);
                    console.log("Output: "+output);
                    res.end(output);
                });
        });
    }
    else 
    {
        res.end("Send Commands.");
    }
});

server.listen(port,hostname,()=>{
    console.log(`Server Running ${hostname}:${port}`);
})