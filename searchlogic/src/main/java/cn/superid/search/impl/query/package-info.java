/**
 * <p>
 * Query through the following url:
 * <pre>
 *
 *   POST searcherIp:9700/query/xxx
 * </pre>
 * and with following class in json:
 * <ul>
 * <li>{@link cn.superid.search.entities.time.announcement.AnnouncementQuery AnnouncementQuery}</li>
 * <li>{@link cn.superid.search.entities.user.affair.AffairQuery AffairQuery}</li>
 * <li>{@link cn.superid.search.entities.user.file.FileQuery FileQuery}</li>
 * </ul>
 * and return results of following class respectively
 * <ul>
 *   <li>{@link cn.superid.search.entities.PageVO PageVO}&lt;{@link cn.superid.search.entities.time.announcement.AnnouncementVO AnnouncementVO}&gt;</li>
 *   <li>{@link cn.superid.search.entities.PageVO PageVO}&lt;{@link cn.superid.search.entities.user.affair.AffairVO AffairVO}&gt;</li>
 *   <li>List&lt;{@link cn.superid.search.entities.user.file.FileSearchVO FileSearchVO}&gt;</li>
 * </ul>
 * </p>
 *
 * <p>
 * Refer to {@link cn.superid.search.impl.query.QueryController} to find the exact query interfaces
 * </p>
 *
 * @author zzt
 * @see cn.superid.search.impl.query.QueryController
 */
package cn.superid.search.impl.query;