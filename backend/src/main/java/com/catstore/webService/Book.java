//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.3.2 生成的
// 请访问 <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a>
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2021.10.12 时间 02:16:57 PM CST
//


package com.catstore.webService;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>book complex type的 Java 类。
 *
 * <p>以下模式片段指定包含在此类中的预期内容。
 *
 * <pre>
 * &lt;complexType name="book"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="stock" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="price" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "book", propOrder = {
        "id",
        "details",
        "title",
        "stock",
        "price",
        "description"
})
public class Book {

    protected int id;
    @XmlElement(required = true)
    protected String details;
    @XmlElement(required = true)
    protected String title;
    protected int stock;
    protected float price;
    @XmlElement(required = true)
    protected String description;

    /**
     * 获取id属性的值。
     */
    public int getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     */
    public void setId(int value) {
        this.id = value;
    }

    /**
     * 获取details属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getDetails() {
        return details;
    }

    /**
     * 设置details属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDetails(String value) {
        this.details = value;
    }

    /**
     * 获取title属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置title属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * 获取stock属性的值。
     */
    public int getStock() {
        return stock;
    }

    /**
     * 设置stock属性的值。
     */
    public void setStock(int value) {
        this.stock = value;
    }

    /**
     * 获取price属性的值。
     */
    public float getPrice() {
        return price;
    }

    /**
     * 设置price属性的值。
     */
    public void setPrice(float value) {
        this.price = value;
    }

    /**
     * 获取description属性的值。
     *
     * @return possible object is
     * {@link String }
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDescription(String value) {
        this.description = value;
    }

}
