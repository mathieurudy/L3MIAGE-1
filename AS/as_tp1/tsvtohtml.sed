1 i <html>\n<head><style>table, th, td { border: 1px solid black; }\ntable { border-collapse: collapse; }\n td,th {padding: 5px;}</style></head>\n<body><table><tr><th>Name</th><th>Position</th><th>Office</th><th>Age</th><th>Start date</th><th>Salary</th></tr>\n\n 
$ a </table></body></html>
s/^/\<tr\>\<td\>/
s/\t\t*/\<\/td\>\<td\>/g
s/$/\<\/td\>\<\/tr\>/
