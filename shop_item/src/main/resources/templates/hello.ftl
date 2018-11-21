<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    hello, ${key}
    <hr/>
    <#if age < 18>
        未成年
        <#elseif (age >= 18 && age < 40)>
        成年
        <#elseif (age >= 40 && age < 60)>
        中年
        <#else>
        老年
    </#if>
    <hr/>
    <#list goodslist as goods>
        ${goods.id} ${goods.title} ${goods.ginfo}<br/>
    </#list>
    <hr/>
    ${time?time}<br/>
    ${time?date}<br/>
    ${time?datetime}<br/>
    ${time?string("yyyy年MM月dd日 HH时mm分ss秒")}<br/>
    <hr/>
    ${money?string("￥#,###.##")}
    <hr/>
    <#if person??>
        person对象不为空
        <#else>
        person对象为空
    </#if>
    <hr/>
    ${person!'默认值'}
</body>
</html>