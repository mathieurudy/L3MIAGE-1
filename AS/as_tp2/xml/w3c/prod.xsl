<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE prod [
<!ENTITY nbsp "&#160;">
]>

<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
                
<xsl:output method="html" encoding="UTF-8"/>

<xsl:template match="/">
<html>
<head>
  <title>XML--Règles de grammaire</title>
</head>
<body>
<h1>La grammaire de XML</h1>
Ce document a été produit par la feuille de style <a href="prod.xsl">prod.xsl</a>
à partir de la dernière <a href="REC-xml-20081126.xml">recommandation</a> du 
<a href="http://www.w3.org/">W3C</a>.

<ul style="list-style-type:none">
  <xsl:apply-templates select="//prod" />
</ul>
</body>
</html>
</xsl:template>

<xsl:template match="prod">

  <li>(<xsl:value-of select="./@num"/>)
  <xsl:apply-templates select="./lhs" /> ::= <xsl:apply-templates select="./rhs" />
<xsl:text>
</xsl:text>
 <xsl:apply-templates select="./com" />
<xsl:text> 
</xsl:text></li>
</xsl:template>

<xsl:template match="lhs">
  <b><xsl:value-of select="."/></b>
</xsl:template>
<xsl:template match="rhs">
  <xsl:value-of select="."/><br/>
<!--  <xsl:apply-templates select="./*" /> -->
</xsl:template>
<!--  <xsl:for-each select="*">
  <xsl:choose>
  <xsl:when test=".=nt">nt <xsl:apply-templates select="."/></xsl:when>
  <xsl:otherwise>oth <xsl:value-of select="."/></xsl:otherwise>
</xsl:choose>
<xsl:text> </xsl:text>
</xsl:for-each>
</xsl:template>
-->
<xsl:template match="nt">
  <xsl:value-of select="."/><xsl:text> </xsl:text>
</xsl:template>
<xsl:template match="com">
  <br/>&nbsp;&nbsp;&nbsp;&nbsp;<i><span style="font-size:80%">Commentaires: <xsl:value-of select="."/></span></i>
<xsl:text> 
</xsl:text>
</xsl:template>
<xsl:template match="text()">
<xsl:value-of select="."/>
</xsl:template>
</xsl:stylesheet>
