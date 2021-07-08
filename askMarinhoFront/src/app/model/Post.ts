import { Comment } from "./Comment"
import { Tag } from "./Tag"
import { User } from "./User"

export class Post {
    public idPost: number
    public title: string
    public description: string
    public urlImage: string
    public date: Date
    public comment: Comment[]
    public userPost: User
    public tagRelation: Tag[]
    public userUpvotePost: User[]
    public userReportPost: User[]
}