package com.agilemaster.findoil.bootstrap

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import com.agilemaster.findoil.domain.Article
import com.agilemaster.findoil.domain.ArticleCatalog
import com.agilemaster.findoil.domain.User
import com.agilemaster.findoil.repository.ArticleRepository

@Service
class ArticleInitService {
	
	@Autowired
	ArticleRepository articleRepository;
	
	/**
	 * note :not use
	 * @param catalog 行情资讯 catalog
	 * @return
	 */
	def initMarketInfo(ArticleCatalog catalog,User user){
		Calendar now = Calendar.getInstance()
		def catalogList = [catalog]
		if(articleRepository.countByCatalog(catalog.getId())==0){
			Article article = new Article();
			article.setCatalogs(catalogList)
			article.setAuthor(user);
			article.setDateCreated(now)
			article.setIndexShow(true);
			article.setOnline(true);
			article.setLastUpdated(now)
			article.setCode("2015年的基础油市场会怎样呢")
			article.setMetaDesc("2015年的基础油市场会怎样呢")
			article.setKeywords("2015年的基础油市场会怎样呢")
			article.setIndexSequence(1)
			article.setTitle("2015年的基础油市场会怎样呢")
			article.setContent("""<p>　2014年对于国内基础油市场来说，注定是不寻常的一年。&ldquo;旺季不旺、淡季更淡&rdquo;成为基础油市场的直观表述。</p> 

<p>　　步入2015年，整体交投氛围延续冷清迹象，主营炼厂价格不断走低。目前国内一类150SN主流价格在6000-6400元/吨，二类150N主流价格在7000-7400元/吨。</p> 

<p>　　2015年中国市场又将迎来多套II类基础油装置的投产。其中包括南京炼油厂，中海油气泰州石化，茂名石化和广州金姬石化。多套新增II类基础油装置的累计年产能将高达149万吨，据金银岛统计，截止至2015年中国II类基础油装置的总年产能将达到261万吨。</p> 

<p><strong>　　一.2014年I、II类基础油市场整体发展状况</strong></p> 

<p><img alt="" src="http://www.sinolub.com/uploadfile/article/uploadfile/201501/20150128024719206.jpg" style="height:253px; width:497px" /></p> 

<p>&nbsp;</p> 

<p>　　从图一不难看出，I类基础油自6月份价格小幅下探，直至9月份进入深跌。上半年，两大石油集团外销资源有限，国内I类基础油价格得到小幅提振。随着6月市场外销资源增加，加之夏季来临，气温升高，调和淡季如期而至，低迷走势延续了整个夏季。</p> 

<p>　　三季度 &quot;金九银十&quot; 传统润滑油销售旺季来临，但下游需求萎靡不振影响，呈现&quot;旺季不旺&quot;的局面。9月之后，受国际原油价格大幅下行影响，国内基础油贸易商降价促销的迫切期望和下游操作意向低迷的心态，导致基础油市场的交投氛围冷清。</p> 

<p><img alt="" src="http://www.sinolub.com/uploadfile/article/uploadfile/201501/20150128024733691.jpg" style="height:256px; width:489px" /></p> 

<p>&nbsp;</p> 

<p>　　2014年国内II类基础油均价整体下行，截止12月末，II类60N均价为7900元/吨，同比跌幅为10.09%;150N均价为7763元/吨，同比跌幅为15.39%;500N均价为7925元/吨，同比跌幅为20.85%。</p> 

<p>　　目前国内对II基础油的需求量逐年递增，但由于我国目前生产II类基础油的炼厂有限，因此进口II类资源占据市场一席之地。</p> 

<p>　　从图二中可以很明显的看出，II类60N、150N、500N几个主营牌号下半年整体呈下行走势，其中60N及150N在3月份出现小幅上行趋 势，主要原因在于当时国内市场供应紧张。6月份后，随着调和淡季的到来，加之原油价格大幅跳水，而下游需求仍无好转，导致基础油价格一路下跌，降至谷底。</p> 

<p><strong>　　二.2015影响基础油市场的各类因素</strong></p> 

<p>　　1.原油大跌打击基础油心态</p> 

<p><img alt="" src="http://www.sinolub.com/uploadfile/article/uploadfile/201501/20150128024748905.jpg" style="height:255px; width:500px" /></p> 

<p>&nbsp;</p> 

<p>　　如上图三所示，原油走势在2014年下半年呈现瀑布式下跌，直至目前一路跌破50美元/桶。同时，美国原油产量屡创新高，利比亚、伊拉克等 OPEC成员国产量继续增长，全球供应宽松的压力进一步加剧，原油价格一落千丈。短期来看，国际油价仍未触底反弹，利空因素持续升温打压基础油市场积极 性，业者普遍担忧心态，上游内外交困，下游需求疲软。</p> 

<p>　　2.宏观经济增速缓慢影响润滑油企业</p> 

<p>　　国内经济增速缓慢，12月份汇丰制造业PMI初值49.5，跌至7个月低位。经济环境的不景气导致润滑油企业发展受阻，作为原料的基础油市场屡屡受挫并非一日之功，多数下游中小型企业在低迷的经济环境下已自行调节，大多降低开工量，减产面对艰难的市场环境。</p> 

<p>　　3.供需矛盾加剧</p> 

<p>　　2014年基础油市场基本全年维持供应过剩的局面，产能过剩问题已是基础油行业必须面对的问题之一。部分上游生产企业已尽量放松其生产力度，多 数炼厂在各方压力下仍低负荷维持生产，产量的减少尚不及需求缩水的幅度快。2015年中国市场又将迎来多套II类基础油装置的投产。其中包括南京炼油厂， 中海油气泰州石化，茂名石化和广州金姬石化。</p> 

<p>　　多套新增II类基础油装置的累计年产能将高达149万吨，截止至2015年中国II类基础油装置的总年产能将达到261万吨。而相对于2015 年II类基础油需求量的增幅来看，供应明显呈现出过剩的态势。生产企业无法从根源上解决供应过剩的问题，面临的便是库存积压，导致价格一再下跌。</p> 

<p>　　4、台塑3月份检修的预期带来一定利好</p> 

<p>　　前期进口商对于台塑集团基础油装置将于3月份检修的预期给市场带来一定利好，然而在外盘跌势不止、消费税&quot;三连涨&quot;的影响下，进口贸易商补库热情也大大衰减。</p> 

<p>　　5、汽车制造业赋予润滑油产业一定利好</p> 

<p>　　虽然，我国汽车行业受国际宏观调控及限购政策等因素的影响，汽车消费量增速有所降低，但受我国汽车保有量的支撑，已经为润滑油产业培养出巨大的 汽车润滑油市场消费能力。截至2013年底我国乘用车保有量1.37亿辆。业内测算，2013年中国汽车售后服务市场规模超过4500亿元，按照目前国内 汽车销量增速，预计2015年整个汽车售后服务市场规模将超过7600亿元。</p> 

<p>　　因此，后市润滑油产业的发展仍较乐观。随着汽车产业应对环保压力进行的升级，这也对润滑油产业提出了更高的要求，更新换代将是未来润滑油行业需要面对的首要问题。</p> 

<p>　　总之，短期内基础油市场外围利空因素交织，下游需求持续疲软，国际油价一路下跌以及资源过剩将持续影响基础油市场，炼厂方面为缓解压力价格一降再降，而商家为避险则陷入观望情绪。</p> """)
			
		}
	}
	

	
	/**
	 *
	 * @param catalog 行情资讯 catalog
	 * @return
	 */
	def initStaticContent(ArticleCatalog catalog){
		
	}

}
