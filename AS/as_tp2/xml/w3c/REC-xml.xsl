<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

<xsl:import href="diffspec.xsl"/>

<xsl:param name="show.diff.markup" select="0"/>

<xsl:param name="additional.css">
<xsl:if test="$show.diff.markup != '0'">
<xsl:text>
div.diff-add  { background-color: #FFFF99; }
div.diff-del  { text-decoration: line-through; }
div.diff-chg  { background-color: #99FF99; }
div.diff-off  {  }

span.diff-add { background-color: #FFFF99; }
span.diff-del { text-decoration: line-through; }
span.diff-chg { background-color: #99FF99; }
span.diff-off {  }

td.diff-add   { background-color: #FFFF99; }
td.diff-del   { text-decoration: line-through }
td.diff-chg   { background-color: #99FF99; }
td.diff-off   {  }
</xsl:text>
</xsl:if>
<xsl:text>
em.rfc2119 { text-transform: lowercase;
             font-variant: small-caps;
             font-style: normal; }
</xsl:text>
</xsl:param>

<xsl:variable name="prev.errata.loc">
  <xsl:value-of select="//preverrataloc/@href"/>
</xsl:variable>

<xsl:template match="loc[@role='erratumref']">
  <xsl:choose>
    <xsl:when test="$show.diff.markup='0'">
      <!-- nop -->
    </xsl:when>
    <xsl:otherwise>
      <a xmlns="http://www.w3.org/1999/xhtml">
        <xsl:attribute name="href">
          <xsl:value-of select="$prev.errata.loc"/>#<xsl:value-of select="@href"/>
        </xsl:attribute>
        <xsl:text>[</xsl:text><xsl:value-of select="@href"/><xsl:text>]</xsl:text>
      </a>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<xsl:template match="loc[@role='erratumref']" mode="text">
  <xsl:choose>
    <xsl:when test="$show.diff.markup='0'">
      <!-- nop -->
    </xsl:when>
    <xsl:otherwise>
      <xsl:apply-imports/>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

  <!-- loc: a Web location -->
  <!-- outside the header, it's a normal cross-reference -->
  <xsl:template match="loc">
    <xsl:if test="starts-with(@href, '#')">
      <xsl:if test="not(key('ids', substring-after(@href, '#')))">
        <xsl:message terminate="yes">
          <xsl:text>Internal loc href to </xsl:text>
          <xsl:value-of select="@href"/>
          <xsl:text>, but that ID does not exist in this document.</xsl:text>
        </xsl:message>
      </xsl:if>
    </xsl:if>
    
    <a xmlns="http://www.w3.org/1999/xhtml" href="http://www.w3.org/TR/REC-xml/%7B@href%7D">
      <xsl:if test="@role">
        <xsl:attribute name="rel"><xsl:value-of select="@role"/></xsl:attribute>
      </xsl:if>
      <xsl:choose>
        <xsl:when test="count(child::node())=0">
          <xsl:value-of select="@href"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:apply-templates/>
        </xsl:otherwise>
      </xsl:choose>
    </a>
  </xsl:template>
  
  
<!-- author: an editor of a spec -->
<!-- only appears in authlist -->
<!-- called in <dl> context -->
<xsl:template match="author">
	<dd xmlns="http://www.w3.org/1999/xhtml">
		<xsl:apply-templates/>
		<xsl:if test="@role = '2e'">
			<xsl:text> - Second Edition</xsl:text>
		</xsl:if>
		<xsl:if test="@role = '3e'">
			<xsl:text> - Third Edition</xsl:text>
		</xsl:if>
		<xsl:if test="@role = '4e'">
			<xsl:text> - Fourth Edition</xsl:text>
		</xsl:if>
	</dd>
</xsl:template>


<!-- mode: number -->
<xsl:template mode="number" match="prod">
  <xsl:text>[</xsl:text>
  <xsl:apply-templates select="." mode="number-simple"/>
  <xsl:text>]</xsl:text>
</xsl:template>

<!-- mode: number-simple -->
<xsl:template mode="number-simple" match="prod">
  <!--xsl:number level="any" count="prod[not(@diff='add')]"/-->
  <xsl:value-of select="@num"/>
</xsl:template>

<!--xsl:template name="autogenerated-appendices">
  <div xmlns="http://www.w3.org/1999/xhtml" class="div1">
    <xsl:text>
</xsl:text>
    <h2 xmlns="http://www.w3.org/1999/xhtml">
      <xsl:call-template name="anchor">
        <xsl:with-param name="conditional" select="0"/>
        <xsl:with-param name="default.id" select="'productions'"/>
      </xsl:call-template>
      <xsl:text>J List of productions (Non-Normative)</xsl:text>
    </h2>
    <p>This non-normative appendix recapitulates all the grammar productions from the body
     and normative appendices of the specification.</p>
    <xsl:for-each select="//scrap">
      <xsl:apply-templates select="."/>
    </xsl:for-each>
  </div>
</xsl:template>

<xsl:template name="autogenerated-appendices-toc">
  <xsl:text>J </xsl:text>
  <a xmlns="http://www.w3.org/1999/xhtml" href="#productions">List of productions</a>
  <xsl:text> (Non-Normative)</xsl:text>
  <br xmlns="http://www.w3.org/1999/xhtml"/>
  <xsl:text>
</xsl:text>
</xsl:template-->


  <xsl:template match="bibl">
    <dt xmlns="http://www.w3.org/1999/xhtml" class="label">
      <xsl:if test="@id">
        <a name="{@id}" id="{@id}"/>
      </xsl:if>
      <xsl:choose>
        <xsl:when test="@key">
          <xsl:value-of select="@key"/>
        </xsl:when>
        <xsl:otherwise>
          <xsl:value-of select="@id"/>
        </xsl:otherwise>
      </xsl:choose>
    </dt>
    <dd xmlns="http://www.w3.org/1999/xhtml">
      <xsl:apply-templates/>
      <xsl:if test="@href">
        <xsl:text>  (See </xsl:text>
        <xsl:value-of select="@href"/>
        <xsl:text>.)</xsl:text>
      </xsl:if>
    </dd>
  </xsl:template>

  <xsl:template match="bibl[@diff]" priority="1">
    <xsl:variable name="dt">
      <xsl:if test="@id">
	<a xmlns="http://www.w3.org/1999/xhtml" name="{@id}"/>
      </xsl:if>
      <xsl:choose>
	<xsl:when test="@key">
	  <xsl:value-of select="@key"/>
	</xsl:when>
	<xsl:otherwise>
	  <xsl:value-of select="@id"/>
	</xsl:otherwise>
      </xsl:choose>
    </xsl:variable>
    <xsl:variable name="dd">
      <xsl:apply-templates/>
      <xsl:if test="@href">
        <xsl:text>  (See </xsl:text>
        <xsl:value-of select="@href"/>
        <xsl:text>.)</xsl:text>
      </xsl:if>
    </xsl:variable>

    <xsl:choose>
      <xsl:when test="@diff and $show.diff.markup != 0">
	<dt xmlns="http://www.w3.org/1999/xhtml" class="label">
	  <span class="diff-{@diff}">
	    <xsl:copy-of select="$dt"/>
	  </span>
	</dt>
	<dd xmlns="http://www.w3.org/1999/xhtml">
	  <div class="diff-{@diff}">
	    <xsl:copy-of select="$dd"/>
	  </div>
	</dd>
      </xsl:when>
      <xsl:when test="@diff='del' and $show.diff.markup = 0">
	<!-- suppressed -->
      </xsl:when>
      <xsl:otherwise>
	<dt xmlns="http://www.w3.org/1999/xhtml" class="label">
	  <xsl:copy-of select="$dt"/>
	</dt>
	<dd xmlns="http://www.w3.org/1999/xhtml">
	  <xsl:copy-of select="$dd"/>
	</dd>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>

  <xsl:template match="titleref">
    <xsl:choose>
      <xsl:when test="@href">
        <a xmlns="http://www.w3.org/1999/xhtml" href="http://www.w3.org/TR/REC-xml/%7B@href%7D">
          <cite>
            <xsl:apply-templates/>
          </cite>
        </a>
      </xsl:when>
      <xsl:when test="ancestor-or-self::bibl/@href">
        <a xmlns="http://www.w3.org/1999/xhtml" href="http://www.w3.org/TR/REC-xml/%7B../@href%7D">
          <cite>
            <xsl:apply-templates/>
          </cite>
        </a>
      </xsl:when>
      <xsl:otherwise>
        <cite xmlns="http://www.w3.org/1999/xhtml">
          <xsl:apply-templates/>
        </cite>
      </xsl:otherwise>
    </xsl:choose>
  </xsl:template>


  <xsl:template match="com">
    <xsl:if test="not($show.diff.markup=0 and loc[@role='erratumref'] and (ancestor-or-self::*/@diff or preceding-sibling::*/@diff))">
			<xsl:choose>
				<xsl:when test="preceding-sibling::*[1][name()='rhs']">
					<td xmlns="http://www.w3.org/1999/xhtml">
						<xsl:if test="ancestor-or-self::*/@diff and $show.diff.markup != 0">
							<xsl:attribute name="class">
								<xsl:text>diff-</xsl:text>
								<xsl:value-of select="ancestor-or-self::*/@diff"/>
							</xsl:attribute>
						</xsl:if>
						<i>
							<xsl:text>/* </xsl:text>
							<xsl:apply-templates/>
							<xsl:text> */</xsl:text>
						</i>
					</td>
				</xsl:when>
				<xsl:otherwise>
					<tr valign="baseline" xmlns="http://www.w3.org/1999/xhtml">
						<td/><td/><td/><td/>
						<td>
							<xsl:if test="ancestor-or-self::*/@diff and $show.diff.markup != 0">
								<xsl:attribute name="class">
									<xsl:text>diff-</xsl:text>
									<xsl:value-of select="ancestor-or-self::*/@diff"/>
								</xsl:attribute>
							</xsl:if>
							<i>
								<xsl:text>/* </xsl:text>
								<xsl:apply-templates/>
								<xsl:text> */</xsl:text>
							</i>
						</td>
					</tr>
				</xsl:otherwise>
			</xsl:choose>
	  </xsl:if>
  </xsl:template>

<xsl:template match="rfc2119">
  <em xmlns="http://www.w3.org/1999/xhtml" class="rfc2119" title="Keyword in RFC 2119 context"><xsl:apply-templates/></em>
</xsl:template>

<xsl:template match="rfc2119[@diff]">
  <xsl:choose>
    <xsl:when test="$show.diff.markup != 0">
      <span xmlns="http://www.w3.org/1999/xhtml">
        <xsl:attribute name="class">diff-<xsl:value-of select="@diff"/></xsl:attribute>
        <em xmlns="http://www.w3.org/1999/xhtml" class="rfc2119" title="Keyword in RFC 2119 context"><xsl:apply-templates/></em>
      </span>
    </xsl:when>
    <xsl:otherwise>
      <em xmlns="http://www.w3.org/1999/xhtml" class="rfc2119" title="Keyword in RFC 2119 context"><xsl:apply-templates/></em>
    </xsl:otherwise>
  </xsl:choose>
</xsl:template>

<!-- emph: in-line emphasis -->
<!-- equates to HTML <em> -->
<!-- the role attribute could be used for multiple kinds of
		 emphasis, but that would not be kind -->
<xsl:template match="emph">
	<em xmlns="http://www.w3.org/1999/xhtml">
		<xsl:call-template name="anchor">
			<xsl:with-param name="conditional" select="1"/>
		</xsl:call-template>
	  <xsl:apply-templates/>
	</em>
</xsl:template>

</xsl:stylesheet>