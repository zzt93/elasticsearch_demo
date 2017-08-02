/**
 * <p>
 * This package is used to save/update/delete entities you want to search.
 * </p>
 *
 * <p>
 * In order to de-couple with the business system, message is used. Business system first invokes
 * the a REST message producer method at
 * <pre>
 *   producerIp:9700/api/index
 * </pre>
 * with
 * the {@link cn.superid.common.notification.dto.NotificationMessage NotificationMessage},
 * then message queue will
 * then send the message to {@link cn.superid.search.impl.save.MessageReceiver} at right time,
 * which business system doesn't care.
 * </p>
 *
 * <p>
 * An example of invocation is like following:
 * </p>
 * <pre>
 *
 *       NotificationMessage message = new NotificationMessage();
 *       message.setType(NotificationType.INDEX_ANNOUNCEMENT);
 *       Map<String,Object> map = new HashedMap();
 *       // "data" is a fixed key
 *       map.put("data",announcement);
 *       message.setParam(map);
 *
 *       // POST or DELETE
 * </pre>
 *
 *
 * <p></p>
 * {@link cn.superid.search.impl.save.MessageReceiver} handle the following mapping:
 * <ul>
 * <li>save: {@link org.springframework.web.bind.annotation.RequestMethod#POST POST}</li>
 * <li>update: {@link org.springframework.web.bind.annotation.RequestMethod#POST POST}</li>
 * <li>delete: {@link org.springframework.web.bind.annotation.RequestMethod#DELETE DELETE}</li>
 * </ul>
 *
 * @author zzt
 * @see cn.superid.search.impl.save.MessageReceiver
 */
package cn.superid.search.impl.save;