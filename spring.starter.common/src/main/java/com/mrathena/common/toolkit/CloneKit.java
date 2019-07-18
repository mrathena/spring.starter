package com.mrathena.common.toolkit;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author mrathena on 2019/5/27 11:16
 */
@Slf4j
public class CloneKit {

	private CloneKit() {}

	/**
	 * 克隆对象
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T clone(T object) {
		T newObject = null;
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
			 ObjectOutputStream oos = new ObjectOutputStream(baos)) {
			oos.writeObject(object);
			try (ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				 ObjectInputStream ois = new ObjectInputStream(bais)) {
				newObject = (T) ois.readObject();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return newObject;
	}

}
