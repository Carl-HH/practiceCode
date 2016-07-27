package szjiuzhou.smcms.ipgeoservice.domain;

/**
 * LocationBean.
 * 
 * @author chen.hua
 * @since
 * @param
 */
public class LocationBean {
	String country_code;
	String country_name;
	String region_name;
	String city_name;
	String latitude;
	String longitude;

	/**
	 * @return the country_code
	 */
	public String getCountry_code() {
		return country_code;
	}

	/**
	 * @param country_code
	 *            the country_code to set
	 */
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	/**
	 * @return the country_name
	 */
	public String getCountry_name() {
		return country_name;
	}

	/**
	 * @param country_name
	 *            the country_name to set
	 */
	public void setCountry_name(String country_name) {
		this.country_name = country_name;
	}

	/**
	 * @return the region_name
	 */
	public String getRegion_name() {
		return region_name;
	}

	/**
	 * @param region_name
	 *            the region_name to set
	 */
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}

	/**
	 * @return the city_name
	 */
	public String getCity_name() {
		return city_name;
	}

	/**
	 * @param city_name
	 *            the city_name to set
	 */
	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * (方法作用描述).
	 * 
	 * @author chen.hua
	 * @create 2015年11月10日 下午4:26:18
	 * @since
	 * @param
	 * @return
	 */
	@Override
	public String toString() {
		return "LocationBean [country_code=" + country_code + ", country_name="
				+ country_name + ", region_name=" + region_name
				+ ", city_name=" + city_name + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

}
